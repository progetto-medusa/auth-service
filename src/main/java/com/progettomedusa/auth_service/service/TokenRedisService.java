package com.progettomedusa.auth_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class TokenRedisService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public String consumeToken(String key) {
        String email = redisTemplate.opsForValue().get(key);
        return email;
    }

    public void storeAuthToken(String key, String token) {
        redisTemplate.opsForValue().set(key, token, Duration.ofMinutes(30));
    }
}

