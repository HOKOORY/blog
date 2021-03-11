package com.ho.blogt.service.impl;

import com.ho.blogt.service.TokenService;
import com.ho.blogt.utils.HashEncodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;


@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    RedisTemplate<String, Object> objectRedisTemplate;

    @Override
    public String generatorToken(String str) {
        String token = "";
        try {
            token = HashEncodeUtil.Md5Encode(str);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return token;
    }

    @Override
    public void setToken(String key, Object value) {
        objectRedisTemplate.opsForValue().set(key, value,62208000, TimeUnit.SECONDS);
    }

    @Override
    public void setToken(String key, Object value, long tiemout) {
        objectRedisTemplate.opsForValue().set(key, value,tiemout, TimeUnit.SECONDS);
    }

    @Override
    public Object getToken(String key) {
        return objectRedisTemplate.opsForValue().get(key);
    }

}
