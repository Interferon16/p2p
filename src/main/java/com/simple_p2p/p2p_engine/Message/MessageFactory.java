package com.simple_p2p.p2p_engine.Message;

public class MessageFactory {

    public static Message createTextMessageInstance() {
        Message message = new Message();
        message.setType(MessageType.TEXT);
        message.setTimeStamp(System.currentTimeMillis());
        return message;
    }

    public static Message createDataPacketInstance() {
        Message message = new Message();
        message.setType(MessageType.DATA);
        message.setDataBuffCapacity(8 * 1024);
        message.setTimeStamp(System.currentTimeMillis());
        return message;
    }

    public static Message createHandshakeInstance() {
        Message message = new Message();
        message.setType(MessageType.HANDSHAKE);
        message.setTimeStamp(System.currentTimeMillis());
        return message;
    }

    public static Message createPingInstance() {
        Message message = new Message();
        message.setType(MessageType.PING);
        message.setTimeStamp(System.currentTimeMillis());
        return message;
    }

    public static Message createBootstrapInstance() {
        Message message = new Message();
        message.setType(MessageType.BOOTSTRAP);
        message.setTimeStamp(System.currentTimeMillis());
        return message;
    }
}
