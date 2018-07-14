package com.simple_p2p;

import com.simple_p2p.p2p_engine.server.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SimpleP2P implements CommandLineRunner {

	private Server server;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SimpleP2P.class, args);
	}

	@Bean
	public Server runP2PEngine(){
		this.server = new Server(16161);
		return this.server;
	}

	@Override
	public void run(String... args) throws Exception {
		this.server.run();
	}
}
