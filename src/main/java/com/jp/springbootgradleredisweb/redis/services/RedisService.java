package com.jp.springbootgradleredisweb.redis.services;

import com.jp.springbootgradleredisweb.redis.strategies.DefaultStrategy;
import com.jp.springbootgradleredisweb.redis.strategies.RedisMemStrategyType;
import com.jp.springbootgradleredisweb.redis.strategies.RedisMemorizeStrategy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;

@Service
public class RedisService {
    @Resource
    private RedisTemplate redisTemplate;

    private RedisMemStrategyType currentType;
    private RedisMemorizeStrategy redisMemorizeStrategy;

    public void initRedisMemorizeStrategy(RedisMemStrategyType type) {
        if (type != null && (this.currentType == null || !this.currentType.equals(type))) {
            this.currentType = type;
            try {
                this.redisMemorizeStrategy = (RedisMemorizeStrategy) type.getClazz().getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        } else {
            if (this.redisMemorizeStrategy == null) {
                this.redisMemorizeStrategy = new DefaultStrategy();
            }
        }
    }

    public Object get(String key) {
        return this.redisMemorizeStrategy.parseMemorizeBody(this.redisTemplate.opsForValue().get(key));
    }

    public void set(String key, Object value) {
        this.redisTemplate.opsForValue().setIfAbsent(key, this.redisMemorizeStrategy.getMemorizeBody(value));
    }
}
