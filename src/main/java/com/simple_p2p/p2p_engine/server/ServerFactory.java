package com.simple_p2p.p2p_engine.server;

public class ServerFactory {

    private static Server thisServer = null;

    public synchronized static Server getServerInstance(int port){
        if(thisServer==null) {
            thisServer = new Server(port);
            Thread serverThread = new Thread(thisServer, "serverThread");
            serverThread.start();
        }
        return thisServer;
    }
}
