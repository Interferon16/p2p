package com.simple_p2p.p2p_engine.channel_handlers;

import com.simple_p2p.p2p_engine.channel_handlers.handshake.ClientHandshakeHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.concurrent.CopyOnWriteArrayList;

public class ClientChannelInitializer extends ChannelInitializer {

    private ChannelGroup channelGroup;
    private CopyOnWriteArrayList<Integer> messagesHashBuffer;
    SimpMessageSendingOperations simpMessagingTemplate;

    public ClientChannelInitializer(ChannelGroup channelGroup,CopyOnWriteArrayList<Integer> messagesHashBuffer,SimpMessageSendingOperations simpMessagingTemplate){
        this.channelGroup=channelGroup;
        this.messagesHashBuffer=messagesHashBuffer;
        this.simpMessagingTemplate=simpMessagingTemplate;
    }
    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline().addLast("Drop channels without handshake",new ReadTimeoutHandler(10));
        channel.pipeline().addLast("deserialization",new ObjectDecoder(ClassResolvers.weakCachingResolver(null)));
        channel.pipeline().addLast("serialization",new ObjectEncoder());
        channel.pipeline().addLast(new ClientHandshakeHandler(channelGroup));
        channel.pipeline().addLast(new DuplicatedMessageHandler(messagesHashBuffer));
        channel.pipeline().addLast(new InboundChannelHandler(channelGroup));
        channel.pipeline().addLast(new ToWebFaceHandler(simpMessagingTemplate));
    }
}
