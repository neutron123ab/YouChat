package com.neutron.youchat_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
public class RedisConfig {
    //自定义redisTemplate的连接配置
    @Bean
    public LettuceConnectionFactory redisConnection() {
        RedisStandaloneConfiguration server = new RedisStandaloneConfiguration();
        server.setHostName("121.4.139.32"); //这里写你redis主机地址就好了
        server.setDatabase(0); // 指定数据库
        server.setPort(6379);
        return new LettuceConnectionFactory(server);
    }
}
