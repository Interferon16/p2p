package com.simple_p2p.p2p_engine.channels_inits;

import com.simple_p2p.p2p_engine.channel_handlers.inbound_handlers.DuplicatedMessageHandler;
import com.simple_p2p.p2p_engine.channel_handlers.inbound_handlers.InboundChannelHandler;
import com.simple_p2p.p2p_engine.channel_handlers.inbound_handlers.ToWebFaceHandler;
import com.simple_p2p.p2p_engine.channel_handlers.handshake.ServerHandshakeHandler;
import com.simple_p2p.p2p_engine.channel_handlers.outbound_handlers.MessageUpdaterOutboundHandler;
import com.simple_p2p.p2p_engine.server.Settings;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import java.util.concurrent.CopyOnWriteArrayList;

public class ServerChannelInitializer extends ChannelInitializer {

    private Settings settings;

    public ServerChannelInitializer(Settings settings){
        this.settings = settings;
    }
    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline().addLast("Drop channels without handshake",new ReadTimeoutHandler(10));
        channel.pipeline().addLast("deserialization",new ObjectDecoder(ClassResolvers.weakCachingResolver(null)));
        channel.pipeline().addLast("serialization",new ObjectEncoder());
        channel.pipeline().addLast("Add timestamp and hash",new MessageUpdaterOutboundHandler());
        channel.pipeline().addLast(new ServerHandshakeHandler(settings.getConnectedChannelGroup()));
        channel.pipeline().addLast(new DuplicatedMessageHandler(settings.getMessagesHashBuffer()));
        channel.pipeline().addLast(new InboundChannelHandler(settings.getConnectedChannelGroup()));
        channel.pipeline().addLast(new ToWebFaceHandler(settings.getMessagingTemplate()));
    }
}
