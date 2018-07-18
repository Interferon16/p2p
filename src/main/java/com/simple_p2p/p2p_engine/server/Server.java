package com.simple_p2p.p2p_engine.server;

import com.simple_p2p.p2p_engine.Utils.HashWork;
import com.simple_p2p.p2p_engine.Utils.NetworkEnvironment;
import com.simple_p2p.p2p_engine.channel_handlers.ServerChannelInitializer;
import com.simple_p2p.p2p_engine.client.Client;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;

public class Server implements Runnable{

    private int port;
    private Channel listenerChannel;
    private ChannelGroup channelGroup;
    private Client client;
    private String myHash;
    private InetAddress localAddress;
    private String localMacAddress;
    private InetAddress externalAddress;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public Server() {

    }

    public Server(int port) {
        this.port = port;
        this.channelGroup = new DefaultChannelGroup(new DefaultEventExecutor());
    }

    public void run() {
        getMyInfo();
        showMyInfo();
        try {
            startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getMyInfo() {
        this.myHash = HashWork.giveMyHash();
        this.localAddress = NetworkEnvironment.getLocalAddress();
        this.localMacAddress = NetworkEnvironment.getLocalMacAddressReadable();
        this.externalAddress = NetworkEnvironment.getExternalAddress();
    }

    private void showMyInfo() {
        logger.info("My hash: " + myHash);
        logger.info("My local address: " + localAddress + " | mac address: " + localMacAddress);
        logger.info("My external address: " + externalAddress);
    }

    private void startServer() throws Exception {
        EventLoopGroup listener = new NioEventLoopGroup();
        EventLoopGroup connectionsLoop = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(listener, connectionsLoop);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 128);
            serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            serverBootstrap.childHandler(new ServerChannelInitializer(channelGroup));
            boolean listenerBind = false;
            port--;
            int connectionPortCounts = 100;
            while (!listenerBind) {
                if(connectionPortCounts<1){
                    logger.error("All using port is bind or restricted, check network policy or multiple client is running");
                    System.exit(1);

                }
                connectionPortCounts--;
                port++;
                try {
                    listenerChannel = serverBootstrap.bind(port).sync().channel();
                    listenerBind = true;
                    logger.info("Listener is bind on port: "+port);
                } catch (Exception e) {
                    logger.warn("Port: " + port + " is already in use try another one");
                }
            }

            logger.info("Server start");
            client = startClient(channelGroup,connectionsLoop);





            listenerChannel.closeFuture().sync();
        } finally {
            connectionsLoop.shutdownGracefully();
            listener.shutdownGracefully();


        }
    }

    private Client startClient(ChannelGroup channelGroup, EventLoopGroup connectionsLoop){
        client = new Client(channelGroup, connectionsLoop);
        try {
            client.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }



    public Client getClient() {
        return client;
    }

    public ChannelGroup getChannelGroup() {
        return channelGroup;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
