package com.simple_p2p.p2p_engine.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import com.simple_p2p.p2p_engine.channel_handlers.CommonChannelInitializer;

import org.jboss.logging.Logger;

public class Client {
    private EventLoopGroup connections;
    private ChannelGroup channelGroup;
    private Bootstrap clientBootstrap;
    private Logger logger = Logger.getLogger(Client.class.getName());


    public Client(ChannelGroup channelGroup, EventLoopGroup connections) {
        this.channelGroup = channelGroup;
        this.connections = connections;
    }

    public Client run() throws Exception {
        logger.info("client start");
        clientBootstrap = new Bootstrap();
        clientBootstrap.group(connections);
        clientBootstrap.channel(NioSocketChannel.class);
        clientBootstrap.handler(new CommonChannelInitializer(channelGroup));
        return this;
    }

    public boolean connect(String address, int port){
        Channel channel=clientBootstrap.connect(address, port).channel();
        channelGroup.add(channel);
        return channel.isRegistered();
    }
}
