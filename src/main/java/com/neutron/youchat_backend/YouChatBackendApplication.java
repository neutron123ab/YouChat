package com.neutron.youchat_backend;

import com.neutron.youchat_backend.nettyServer.WebSocketServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@MapperScan("com.neutron.youchat_backend.mapper")
@SpringBootApplication
public class YouChatBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(YouChatBackendApplication.class, args);
    }

}
