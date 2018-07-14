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
}
