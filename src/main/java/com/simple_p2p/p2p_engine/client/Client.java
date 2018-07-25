package com.simple_p2p.p2p_engine.client;

import com.simple_p2p.entity.KnownUsers;
import com.simple_p2p.p2p_engine.Utils.NetworkEnvironment;
import com.simple_p2p.p2p_engine.channels_inits.ClientChannelInitializer;
import com.simple_p2p.p2p_engine.server.Settings;
import com.simple_p2p.repository.MessageTableRepository;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import io.netty.util.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import java.net.InetAddress;
import java.security.Security;
import java.util.concurrent.CopyOnWriteArrayList;


public class Client {
    private EventLoopGroup connectionsLoop;
    private Bootstrap clientBootstrap;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private InetAddress localAddress;
    private Settings settings;


    public Client(EventLoopGroup connectionsLoop, Settings settings) {
        this.settings=settings;
        this.connectionsLoop = connectionsLoop;
        this.localAddress = NetworkEnvironment.getLocalAddress();
    }

    public void run() throws Exception {
        clientBootstrap = new Bootstrap();
        clientBootstrap.group(connectionsLoop);
        clientBootstrap.channel(NioSocketChannel.class);
        clientBootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 500);
        clientBootstrap.handler(new ClientChannelInitializer(settings));
        logger.info("Client start");
        connectOnStart();
    }

    private void connectOnStart(){
        for(int i=80;i<87;i++){
            int startPort = 16160;
            for(int j=0;j<2;j++){
                startPort++;
                String address = "192.168.0."+i;
                if(!address.equals(localAddress.getHostAddress())){
                    connect(address,startPort);
                }
            }
        }
    }

    public void connect(String address, int port){
        ChannelFuture channelFuture=clientBootstrap.connect(address, port);
        channelFuture.awaitUninterruptibly();

        assert channelFuture.isDone();

        if (channelFuture.isCancelled()) {

        } else if (!channelFuture.isSuccess()) {

            logger.info("Connection is failed to "+address+":"+port);
            channelFuture.channel().close().awaitUninterruptibly();
        } else {
            logger.info("Connection is established to "+address+":"+port);
        }
    }
}
