package com.simple_p2p;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import javax.sql.DataSource;
import java.util.concurrent.CopyOnWriteArrayList;

@SpringBootApplication
public class SimpleP2P {


	public static void main(String[] args) throws Exception {
		SpringApplication.run(SimpleP2P.class, args);
	}

}
