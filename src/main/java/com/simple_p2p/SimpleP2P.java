package com.simple_p2p;

import com.simple_p2p.p2p_engine.p2pcontrol.impl.P2PServerControlImpl;
import com.simple_p2p.p2p_engine.p2pcontrol.interfaces.P2PServerControl;
import com.simple_p2p.p2p_engine.server.Server;
import com.simple_p2p.p2p_engine.server.ServerFactory;
import com.simple_p2p.p2p_engine.server.Settings;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.DefaultEventExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import javax.sql.DataSource;
import java.util.concurrent.CopyOnWriteArrayList;

@SpringBootApplication
public class SimpleP2P {


	public static void main(String[] args) throws Exception {
		SpringApplication.run(SimpleP2P.class, args);
	}

	@Bean
	@Autowired
	public Settings buildSettings(SimpMessageSendingOperations messagingTemplate){
		Settings settings=Settings.getInstatce();
		settings.setMessagingTemplate(messagingTemplate);
		settings.setListener_port(16161);
		settings.setConnectedChannelGroup(new DefaultChannelGroup(new DefaultEventExecutor()));
		settings.setMessagesHashBuffer(new CopyOnWriteArrayList<>());
		return settings;
	}

	@Bean
	@Autowired
	public P2PServerControl p2PServerControl(Settings settings){
		Server server = ServerFactory.getServerInstance(settings);
		return new P2PServerControlImpl(server,settings);
	}

	@Bean
	public DataSource dataSource() {
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName("org.sqlite.JDBC");
		dataSourceBuilder.url("jdbc:sqlite:simple_p2p.db");
		return dataSourceBuilder.build();

	}
}
