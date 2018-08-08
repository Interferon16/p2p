package com.simple_p2p.p2p_engine.p2pEngineBeans;

import com.simple_p2p.p2p_engine.enginerepository.DBWriteHandler;
import com.simple_p2p.p2p_engine.p2pcontrol.impl.P2PServerControlImpl;
import com.simple_p2p.p2p_engine.p2pcontrol.interfaces.P2PServerControl;
import com.simple_p2p.p2p_engine.server.Server;
import com.simple_p2p.p2p_engine.server.ServerFactory;
import com.simple_p2p.p2p_engine.server.Settings;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.DefaultEventExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class p2pBeans {
    @Bean
    @Autowired
    public Settings buildSettings(ApplicationContext appCtx){
        Settings settings=Settings.getInstance();
        settings.setSprAppCtx(appCtx);
        settings.setListener_port(16161);
        settings.setConnectedChannelGroup(new DefaultChannelGroup(new DefaultEventExecutor()));
        settings.setMessagesHashBuffer(new CopyOnWriteArrayList<>());
        DBWriteHandler dbWriteHandler = DBWriteHandler.getInstance();
        /*Thread dbWriteHandlerThread = new Thread(dbWriteHandler);
        dbWriteHandlerThread.setName("dbWriteHandlerThread");
        dbWriteHandlerThread.start();*/
        settings.setDbWriteHandler(dbWriteHandler);
        return settings;
    }

    @Bean
    @Autowired
    public P2PServerControl p2PServerControl(Settings settings){
        Server server = ServerFactory.getServerInstance(settings);
        return new P2PServerControlImpl(server,settings);
    }

/*    @Bean
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.sqlite.JDBC");
        dataSourceBuilder.url("jdbc:sqlite:simple_p2p.db");
        SQLiteConfig sqLiteConfig = new SQLiteConfig();
        sqLiteConfig.setSynchronous(SQLiteConfig.SynchronousMode.FULL);
        return dataSourceBuilder.build();

    }*/
   @Bean
    public DataSource dataSourceH2() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.h2.Driver");
        dataSourceBuilder.url("jdbc:h2:file:~/simple_p2pH2");
        dataSourceBuilder.username("sa");
        dataSourceBuilder.password("");
        return dataSourceBuilder.build();
    }
}
