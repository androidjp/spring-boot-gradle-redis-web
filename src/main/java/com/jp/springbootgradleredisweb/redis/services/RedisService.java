package com.jp.springbootgradleredisweb.redis.services;

import com.jp.springbootgradleredisweb.redis.strategies.DefaultStrategy;
import com.jp.springbootgradleredisweb.redis.strategies.RedisMemStrategyType;
import com.jp.springbootgradleredisweb.redis.strategies.RedisMemorizeStrategy;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;

@Service
public class RedisService {
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private RedisConnectionFactory connectionFactory;

    private RedisMemStrategyType currentType;
    private RedisMemorizeStrategy redisMemorizeStrategy;

    private RedisAtomicHandler redisAtomicHandler;

    @PostConstruct
    private void init() {
        this.redisAtomicHandler = new RedisAtomicHandler(this.redisTemplate, this.connectionFactory);
        this.redisMemorizeStrategy = new DefaultStrategy();
    }

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
        this.redisTemplate.opsForValue().set(key, this.redisMemorizeStrategy.getMemorizeBody(value));
    }

    public RedisLock getLock(String key, int expireSeconds) {
        return this.redisAtomicHandler.getLock(key, expireSeconds);
    }
}
