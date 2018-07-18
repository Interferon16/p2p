package com.simple_p2p.p2p_engine.p2penginecontrol.impl;

import com.simple_p2p.p2p_engine.p2penginecontrol.interfaces.P2PServerControl;
import com.simple_p2p.p2p_engine.server.Server;
import org.springframework.beans.factory.annotation.Autowired;

public class P2PServerControlImpl implements P2PServerControl{
    private Server server;


    @Autowired
    private void P2PServerControl(Server server){
        this.server=server;
        System.out.println("controler is on");
    }
}
