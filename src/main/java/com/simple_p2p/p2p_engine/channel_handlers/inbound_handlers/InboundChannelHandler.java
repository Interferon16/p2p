package com.simple_p2p.p2p_engine.channel_handlers.inbound_handlers;

import com.simple_p2p.entity.KnownUsers;
import com.simple_p2p.p2p_engine.Message.Message;
import com.simple_p2p.p2p_engine.enginerepository.DBWriteHandler;
import com.simple_p2p.p2p_engine.enginerepository.KnownUsersTableRepository;
import com.simple_p2p.p2p_engine.server.Settings;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class InboundChannelHandler extends ChannelInboundHandlerAdapter {
    private ChannelGroup channelGroup;
    private AttributeKey<String> userHash = AttributeKey.valueOf("userHash");
    private AttributeKey<Boolean> isAlive = AttributeKey.valueOf("isAilive");
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Settings settings;


    public InboundChannelHandler(Settings settings) {
        this.channelGroup = settings.getConnectedChannelGroup();
        this.settings = settings;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof Message) {
            Message message = (Message) msg;
            logger.info("Message:" + message.getMessage());
            switch (message.getType()) {
                case PING:
                    updateAliveStatus(message.getFrom());
                    break;
                case BOOTSTRAP:
                    updateDBFromBootstrap(message);
                    break;
                default:
                    sendToAll(msg, ctx);
            }
        }
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

    private void updateAliveStatus(String userHash) {
        logger.info("ping");
        for (Channel c : channelGroup) {
            if (c.attr(this.userHash).get().equals(userHash)) {
                c.attr(isAlive).set(true);
            }
        }
    }

    private void updateDBFromBootstrap(Message message) {
        DBWriteHandler dbWriteHandler = settings.getDbWriteHandler();
        KnownUsersTableRepository knownUsersTableRepository = settings.getSprAppCtx().getBean(KnownUsersTableRepository.class);
        logger.info("get bootstrap");
        logger.info("From:" + message.getFrom());
        if (!message.getKnownNode().isEmpty()) {
            for (KnownUsers k : message.getKnownNode()) {
                dbWriteHandler.addToDB(knownUsersTableRepository, k);
            }
        }
    }

    private void sendToAll(Object msg, ChannelHandlerContext ctx) {
        for (Channel c : channelGroup) {
            if (c != ctx.channel()) {
                c.writeAndFlush(msg);
            }
        }
        ctx.fireChannelRead(msg);
    }
}
