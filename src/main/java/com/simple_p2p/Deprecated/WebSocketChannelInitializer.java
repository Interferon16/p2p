package com.simple_p2p.Deprecated;

import com.simple_p2p.p2p_engine.channel_handlers.HttpServerHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpServerCodec;

public class WebSocketChannelInitializer extends ChannelInitializer {

    private ChannelGroup channelGroup;

    public WebSocketChannelInitializer(ChannelGroup channelGroup){
        this.channelGroup=channelGroup;
    }
    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline().addLast("httpServerCodec", new HttpServerCodec());
        channel.pipeline().addLast(new HttpServerHandler());
        channel.pipeline().addLast(new WebSocketChannelInboundHandler(channelGroup));
    }
}
