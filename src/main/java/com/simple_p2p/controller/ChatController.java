package com.simple_p2p.controller;

import com.simple_p2p.model.ChatMessage;
import com.simple_p2p.p2p_engine.Message.Message;
import com.simple_p2p.p2p_engine.Message.MessageFactory;
import com.simple_p2p.p2p_engine.server.Server;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.nio.charset.Charset;


@Controller
public class ChatController {

    @Autowired
    private Server server;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        sendMessageToAllConnections(chatMessage);
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    private void sendMessageToAllConnections(ChatMessage chatMessage) {
        System.out.println("sgdgsgs");
        Message message = MessageFactory.createTextMessageInstance();
        message.setFrom(chatMessage.getSender());
        message.setMessage(chatMessage.getContent());
        String finMess = message.getFrom()+" say: "+message.getMessage()+"\n";
        ByteBuf byteMess = Unpooled.copiedBuffer(finMess.getBytes(Charset.forName("UTF8")));
        this.server.getChannelGroup().writeAndFlush(byteMess);
    }

}
