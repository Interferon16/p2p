package com.simple_p2p.Deprecated.webserver;

import com.simple_p2p.Deprecated.WebSocketChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;


import java.util.logging.Logger;

public class WebSocketServer {

    private int port;
    private Channel listenerChannel;
    private Logger logger;
    private ChannelGroup channelGroup;
    private EventLoopGroup web_socket_listener;
    private EventLoopGroup web_socket_connections;


    public WebSocketServer(int port, EventLoopGroup listener, EventLoopGroup connections, ChannelGroup channelGroup) {
        this.port = port;
        this.logger = Logger.getLogger(WebSocketServer.class.getName());
        this.web_socket_listener = listener;
        this.web_socket_connections = connections;
        this.channelGroup=channelGroup;
    }

    public void run() throws Exception {
        logger.info("web server start");

        ServerBootstrap webSocketBootstrap = new ServerBootstrap();
        webSocketBootstrap.group(web_socket_listener, web_socket_connections);
        webSocketBootstrap.channel(NioServerSocketChannel.class);
        webSocketBootstrap.option(ChannelOption.SO_BACKLOG, 10);
        webSocketBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        webSocketBootstrap.childHandler(new WebSocketChannelInitializer(channelGroup));

        listenerChannel = webSocketBootstrap.bind(port).sync().channel();

        //listenerChannel.closeFuture().sync();
    }

    public void shutDown() {
        web_socket_listener.shutdownGracefully();
        web_socket_connections.shutdownGracefully();
    }

}
