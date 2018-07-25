package com.simple_p2p.p2p_engine.Message;

public class MessageFactory {

    public static Message createTextMessageInstance() {
        Message message = new Message();
        message.setType(MessageType.TEXT);
        return message;
    }

    public static Message createDataPacketInstance() {
        Message message = new Message();
        message.setType(MessageType.DATA);
        message.setDataBuffCapacity(8 * 1024);
        return message;
    }

    public static Message createHandshakeInstance() {
        Message message = new Message();
        message.setType(MessageType.HANDSHAKE);
        return message;
    }

    public static Message createPingInstance() {
        Message message = new Message();
        message.setType(MessageType.PING);
        return message;
    }
}
