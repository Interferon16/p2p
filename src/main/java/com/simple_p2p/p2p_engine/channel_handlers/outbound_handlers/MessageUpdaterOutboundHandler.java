package com.simple_p2p.p2p_engine.channel_handlers.outbound_handlers;

import com.simple_p2p.p2p_engine.Message.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class MessageUpdaterOutboundHandler extends ChannelOutboundHandlerAdapter{
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if(msg instanceof Message){
            Message message = (Message)msg;
            message.setTimeStamp(System.currentTimeMillis());
        }
        super.write(ctx, msg, promise);
    }
}
