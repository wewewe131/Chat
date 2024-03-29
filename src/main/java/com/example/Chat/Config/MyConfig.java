package com.example.Chat.Config;

import com.example.Chat.Socket.SocketFilter;
import com.example.Chat.Socket.UserGroupSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

@Configuration
public class MyConfig {
    private static CopyOnWriteArraySet set = new CopyOnWriteArraySet();

    private static RedisTemplate redisTemplate;



    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        MyConfig.redisTemplate = redisTemplate;
        UserGroupSocket.setRedisTemplate(redisTemplate);
        return redisTemplate;
    }

    @Bean(name = "onlineSet")
    @Scope("singleton")// 单例模式
    public CopyOnWriteArraySet<UserGroupSocket> getSet() {
        UserGroupSocket.setOnlineUserMuster(set);
        return set;
    }

    @Autowired
    private SocketFilter socketFilter;

    @Bean
    public FilterRegistrationBean getSocketFilter() {

        FilterRegistrationBean socketFilter = new FilterRegistrationBean();
        socketFilter.setFilter(this.socketFilter);
        socketFilter.setUrlPatterns(List.of("/socket/*"));
        socketFilter.setOrder(1);
        return socketFilter;
    }


}
