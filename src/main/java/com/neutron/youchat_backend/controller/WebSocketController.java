package com.neutron.youchat_backend.controller;

import com.neutron.youchat_backend.nettyServer.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//开启websocket服务器
@Component
public class WebSocketController implements CommandLineRunner {

    @Autowired
    private WebSocketServer webSocketServer;

    @Override
    public void run(String... args) throws Exception {
        webSocketServer.run(8088);
    }
}
