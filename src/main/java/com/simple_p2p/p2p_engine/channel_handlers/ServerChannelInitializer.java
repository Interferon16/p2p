package com.simple_p2p.p2p_engine.channel_handlers;

import com.simple_p2p.p2p_engine.channel_handlers.handshake.ServerHandshakeHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class ServerChannelInitializer extends ChannelInitializer {

    private ChannelGroup channelGroup;

    public ServerChannelInitializer(ChannelGroup channelGroup){
        this.channelGroup=channelGroup;
    }
    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline().addLast("Drop channels without handshake",new ReadTimeoutHandler(10));
        channel.pipeline().addLast("deserialization",new ObjectDecoder(ClassResolvers.weakCachingResolver(null)));
        channel.pipeline().addLast("serialization",new ObjectEncoder());
        channel.pipeline().addLast(new ServerHandshakeHandler(channelGroup));
        channel.pipeline().addLast(new InboundChannelHandler(channelGroup));
    }
}
