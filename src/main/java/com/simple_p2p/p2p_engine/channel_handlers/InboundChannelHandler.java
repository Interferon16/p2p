package com.simple_p2p.p2p_engine.channel_handlers;

import com.simple_p2p.p2p_engine.Message.Message;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InboundChannelHandler extends ChannelInboundHandlerAdapter {
    private ChannelGroup channelGroup;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public InboundChannelHandler(ChannelGroup channelGroup) {
        this.channelGroup = channelGroup;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Message message = (Message) msg;
        logger.info(message.getMessage());
        for(Channel c:channelGroup){
            if(c!=ctx.channel()){
                c.writeAndFlush("[" + ctx.channel().remoteAddress() + "] " + msg + '\n');
            }
        }
        ctx.fireChannelRead(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        if (cause.getMessage().equals("Удаленный хост принудительно разорвал существующее подключение")) {
            logger.info(ctx.channel().remoteAddress().toString() + " disconnected");
        } else {
            cause.printStackTrace();
        }
        ctx.flush();
        ctx.close();
    }
}
