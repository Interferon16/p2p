package com.simple_p2p.p2p_engine.server;

import io.netty.channel.group.ChannelGroup;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import java.net.InetAddress;
import java.util.concurrent.CopyOnWriteArrayList;

public class Settings {

    public static class SettingsHolder {
        public static final Settings HOLDER_INSTANCE = new Settings();
    }

    public static Settings getInstatce() {
        return SettingsHolder.HOLDER_INSTANCE;
    }

    public Settings() {
        this.listener_port = 16000; //default listener port
    }

    private String myHash;
    private InetAddress localAddress;
    private String localMacAddress;
    private InetAddress externalAddress;
    private int listener_port;
    private ChannelGroup connectedChannelGroup;
    private SimpMessageSendingOperations messagingTemplate;
    private CopyOnWriteArrayList<Integer> messagesHashBuffer;

    public int getListener_port() {
        return listener_port;
    }

    public void setListener_port(int listener_port) {
        this.listener_port = listener_port;
    }

    public ChannelGroup getConnectedChannelGroup() {
        return connectedChannelGroup;
    }

    public void setConnectedChannelGroup(ChannelGroup connectedChannelGroup) {
        this.connectedChannelGroup = connectedChannelGroup;
    }

    public SimpMessageSendingOperations getMessagingTemplate() {
        return messagingTemplate;
    }

    public void setMessagingTemplate(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public CopyOnWriteArrayList<Integer> getMessagesHashBuffer() {
        return messagesHashBuffer;
    }

    public void setMessagesHashBuffer(CopyOnWriteArrayList<Integer> messagesHashBuffer) {
        this.messagesHashBuffer = messagesHashBuffer;
    }

    public String getMyHash() {
        return myHash;
    }

    public void setMyHash(String myHash) {
        this.myHash = myHash;
    }

    public InetAddress getLocalAddress() {
        return localAddress;
    }

    public void setLocalAddress(InetAddress localAddress) {
        this.localAddress = localAddress;
    }

    public String getLocalMacAddress() {
        return localMacAddress;
    }

    public void setLocalMacAddress(String localMacAddress) {
        this.localMacAddress = localMacAddress;
    }

    public InetAddress getExternalAddress() {
        return externalAddress;
    }

    public void setExternalAddress(InetAddress externalAddress) {
        this.externalAddress = externalAddress;
    }
}
