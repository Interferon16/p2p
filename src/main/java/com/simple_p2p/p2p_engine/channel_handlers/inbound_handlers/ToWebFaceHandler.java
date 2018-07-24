package com.simple_p2p.p2p_engine.channel_handlers.inbound_handlers;

import com.simple_p2p.model.ChatMessage;
import com.simple_p2p.p2p_engine.Message.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class ToWebFaceHandler extends ChannelInboundHandlerAdapter {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private SimpMessageSendingOperations simpMessagingTemplate;

    public ToWebFaceHandler(SimpMessageSendingOperations simpMessagingTemplate){
        this.simpMessagingTemplate=simpMessagingTemplate;
    }



    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        messageToWebFace((Message)msg);
        super.channelRead(ctx, msg);
    }


    private void messageToWebFace(Message message){
        logger.warn("send message to web face");
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setType(ChatMessage.MessageType.CHAT);
        chatMessage.setSender(message.getFrom());
        chatMessage.setContent(message.getMessage());
        simpMessagingTemplate.convertAndSend("/topic/public",chatMessage);
    }
}
