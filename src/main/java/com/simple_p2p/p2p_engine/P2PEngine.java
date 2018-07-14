package com.simple_p2p.p2p_engine;


import com.simple_p2p.model.ChatMessage;
import com.simple_p2p.p2p_engine.Message.Message;
import com.simple_p2p.p2p_engine.Message.MessageFactory;
import com.simple_p2p.p2p_engine.client.Client;
import com.simple_p2p.p2p_engine.server.Server;
import io.netty.channel.group.ChannelGroup;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

public class P2PEngine {
    private Server server;
    private Client client;
    private ChannelGroup channelGroup;

    public P2PEngine() throws Exception {

    }

    public P2PEngine run() throws Exception {
        this.server = new Server(16161);
        server.run();
        this.client = this.server.getClient();
        this.channelGroup = this.server.getChannelGroup();
        return this;
    }

}
