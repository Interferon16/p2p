package com.simple_p2p.p2p_engine.Message;

import java.io.Serializable;
import java.util.Arrays;

public class Message implements Serializable {

    private MessageType type;
    private String from;
    private String to;
    private String message;
    private byte[] dataBuff;
    private int dataBuffCapacity;
    private int hash;
    private long timeStamp;

    public Message() {

    }

    public void setDataBuffCapacity(int data_size) {
        this.dataBuff = new byte[data_size];
        this.dataBuffCapacity = data_size;
    }

    public int getDataBuffCapacity() {
        return dataBuffCapacity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public byte[] getDataBuff() {
        return dataBuff;
    }

    public void setDataBuff(byte[] dataBuff) {
        this.dataBuff = dataBuff;
    }

    public int getDataBuffLength() {
        return dataBuff.length;
    }

    public int getHash() {
        return hash;
    }

    public void createHash(int hash) {
        this.hash = this.hashCode();
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message1 = (Message) o;

        if (dataBuffCapacity != message1.dataBuffCapacity) return false;
        if (timeStamp != message1.timeStamp) return false;
        if (type != message1.type) return false;
        if (from != null ? !from.equals(message1.from) : message1.from != null) return false;
        if (to != null ? !to.equals(message1.to) : message1.to != null) return false;
        if (message != null ? !message.equals(message1.message) : message1.message != null) return false;
        return Arrays.equals(dataBuff, message1.dataBuff);
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (from != null ? from.hashCode() : 0);
        result = 31 * result + (to != null ? to.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(dataBuff);
        result = 31 * result + dataBuffCapacity;
        result = 31 * result + (int) (timeStamp ^ (timeStamp >>> 32));
        return result;
    }
}
