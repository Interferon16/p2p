package com.simple_p2p.p2p_engine.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultEventExecutor;
import com.simple_p2p.p2p_engine.Utils.HashWork;
import com.simple_p2p.p2p_engine.Utils.NetworkEnvironment;
import com.simple_p2p.p2p_engine.channel_handlers.CommonChannelInitializer;
import com.simple_p2p.p2p_engine.client.Client;
import org.jboss.logging.Logger;


import java.net.InetAddress;

public class Server {

    private int port;
    private Channel listenerChannel;
    private Logger logger;
    private ChannelGroup channelGroup;
    private Client client;
    private String myHash;
    private InetAddress localAddress;
    private String localMacAddress;
    private InetAddress externalAddress;

    public Server() {
        this(16161);
    }

    public Server(int port) {
        this.port = port;
        this.logger = Logger.getLogger(Server.class.getName());
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

    private void showMyInfo(){
        logger.infof("My hash: "+myHash);
        logger.infof("My local address: "+localAddress+" | mac address: "+localMacAddress);
        logger.infof("My external address: "+externalAddress);
    }

    private void startServer() throws Exception {
        logger.info("server start");
        EventLoopGroup listener = new NioEventLoopGroup();
        EventLoopGroup connections = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(listener, connections);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 128);
            serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            serverBootstrap.childHandler(new CommonChannelInitializer(channelGroup));

            listenerChannel = serverBootstrap.bind(port).sync().channel();

            client = new Client(channelGroup, connections);
            client.run();


            listenerChannel.closeFuture().sync();
        } finally {
            connections.shutdownGracefully();
            listener.shutdownGracefully();


        }
    }

    public Client getClient(){
        return client;
    }

    public ChannelGroup getChannelGroup() {
        return channelGroup;
    }
}
