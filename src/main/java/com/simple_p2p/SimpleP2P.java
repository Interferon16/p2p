package com.simple_p2p;

import com.simple_p2p.p2p_engine.p2pcontrol.impl.P2PServerControlImpl;
import com.simple_p2p.p2p_engine.p2pcontrol.interfaces.P2PServerControl;
import com.simple_p2p.p2p_engine.server.Server;
import com.simple_p2p.p2p_engine.server.ServerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

@SpringBootApplication
public class SimpleP2P {

	private static Server server;

	@Autowired
	private SimpMessageSendingOperations messagingTemplate;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SimpleP2P.class, args);
	}

	@Bean
	public Server runP2PEngine(){
		server = ServerFactory.getServerInstance(16161,this.messagingTemplate);
		return server;
	}

	@Bean
	public P2PServerControl p2PServerControl(){
		P2PServerControl serverControl = new P2PServerControlImpl();
		return serverControl;
	}
}
