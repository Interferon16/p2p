package com.simple_p2p.p2p_engine.channel_handlers;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;

public class CommonChannelInitializer extends ChannelInitializer {

    private ChannelGroup channelGroup;

    public CommonChannelInitializer (ChannelGroup channelGroup){
        this.channelGroup=channelGroup;
    }
    @Override
    protected void initChannel(Channel channel) throws Exception {
        //channel.pipeline().addLast("deserialization",new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
        //channel.pipeline().addLast("serialization",new ObjectEncoder());
        channel.pipeline().addLast(new InboundChannelHandler(channelGroup));
    }
}
