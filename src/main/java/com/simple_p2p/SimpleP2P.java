package com.simple_p2p;

import com.simple_p2p.p2p_engine.server.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SimpleP2P {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SimpleP2P.class, args);
		new Server(16161).run();
	}
}
