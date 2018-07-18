package com.simple_p2p.p2p_engine.server;

import org.springframework.messaging.simp.SimpMessageSendingOperations;

public class ServerFactory {

    private static Server thisServer = null;

    public synchronized static Server getServerInstance(int port, SimpMessageSendingOperations simpMessagingTemplate){
        if(thisServer==null) {
            thisServer = new Server(port,simpMessagingTemplate);
            Thread serverThread = new Thread(thisServer, "serverThread");
            serverThread.start();
        }
        return thisServer;
    }
}
