package com.simple_p2p.p2p_engine;


import com.simple_p2p.p2p_engine.server.Server;

public class Main {
    public static void main(String[] args) throws Exception {
        new Server(16161).run();
    }
}
