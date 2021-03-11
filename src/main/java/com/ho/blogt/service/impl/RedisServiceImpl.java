package com.ho.blogt.service.impl;

import com.ho.blogt.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    RedisTemplate<String, Object> objectRedisTemplate;

    @Override
    public boolean checkConnection() {
        boolean flag;
        RedisConnectionFactory redisConnectionFactory = objectRedisTemplate.getConnectionFactory();
        try {
            RedisConnection redisConnection = redisConnectionFactory.getConnection();
            flag = redisConnection.isClosed();
        } catch (RedisConnectionFailureException e) {
            flag = true;
        }
        if (flag) {
            System.out.println(new Date() + " Redis Connection is Closed");
        } else {
            System.out.println(new Date() + " Redis Connection is Open");
        }
        return !flag;
    }
}
