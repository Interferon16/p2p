package com.simple_p2p.p2p_engine.p2pcontrol.interfaces;

import com.simple_p2p.model.ChatMessage;
import com.simple_p2p.p2p_engine.Message.Message;

public interface P2PServerControl {
    void sendMessageToAllConnect(ChatMessage chatMessage);

}
