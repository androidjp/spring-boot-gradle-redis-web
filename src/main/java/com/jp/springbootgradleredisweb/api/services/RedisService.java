package com.jp.springbootgradleredisweb.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
    @Autowired
    private RedisTemplate redisTemplate;

    public Object get(String key) {
        return this.redisTemplate.opsForValue().get(key);
    }

    public void set(String key, Object value) {
        this.redisTemplate.opsForValue().set(key, value);
    }
}
