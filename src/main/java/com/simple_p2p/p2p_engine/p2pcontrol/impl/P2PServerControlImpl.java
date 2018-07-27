package com.simple_p2p.p2p_engine.p2pcontrol.impl;

import com.simple_p2p.model.ChatMessage;
import com.simple_p2p.p2p_engine.Message.Message;
import com.simple_p2p.p2p_engine.Message.MessageFactory;
import com.simple_p2p.p2p_engine.client.Client;
import com.simple_p2p.p2p_engine.p2pcontrol.interfaces.P2PServerControl;
import com.simple_p2p.p2p_engine.server.Server;
import com.simple_p2p.p2p_engine.server.Settings;
import io.netty.channel.group.ChannelGroup;

import org.springframework.beans.factory.annotation.Autowired;


public class P2PServerControlImpl implements P2PServerControl {
    private Server server;
    private Client client;
    private ChannelGroup connectedChannelsGroup;


    public P2PServerControlImpl(Server server, Settings settings) {
        this.client = server.getClient();
        this.connectedChannelsGroup = settings.getConnectedChannelGroup();
        this.server = server;
    }

    public void sendMessageToAllConnect(ChatMessage chatMessage) {
        if (connectedChannelsGroup.size()>0) {
            Message message = MessageFactory.createTextMessageInstance();
            message.setFrom(chatMessage.getSender());
            message.setMessage(chatMessage.getContent());
            connectedChannelsGroup.writeAndFlush(message);
        }
    }
}