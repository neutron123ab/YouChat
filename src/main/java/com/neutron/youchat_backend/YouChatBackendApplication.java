package com.neutron.youchat_backend;

import com.neutron.youchat_backend.nettyServer.WebSocketServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class YouChatBackendApplication {

//    @Resource
//    private WebSocketServer webSocketServer;

    public static void main(String[] args) {
        SpringApplication.run(YouChatBackendApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        webSocketServer.run(8088);
//    }
}
