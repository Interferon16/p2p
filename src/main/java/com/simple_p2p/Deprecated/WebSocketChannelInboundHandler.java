package com.simple_p2p.Deprecated;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.nio.charset.Charset;

public class WebSocketChannelInboundHandler extends ChannelInboundHandlerAdapter {

    private ChannelGroup channelGroup;

    public WebSocketChannelInboundHandler (ChannelGroup channelGroup){
        this.channelGroup=channelGroup;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //System.out.println(((TextWebSocketFrame) msg).text());
        ctx.writeAndFlush(new TextWebSocketFrame("I received message: "+((TextWebSocketFrame) msg).text()));
        ByteBuf byteBuf = ctx.alloc().buffer().writeBytes(((TextWebSocketFrame) msg).text().getBytes(Charset.forName("UTF8")));
        channelGroup.writeAndFlush(byteBuf);
    }
}
