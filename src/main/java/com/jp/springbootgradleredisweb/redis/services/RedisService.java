package com.jp.springbootgradleredisweb.redis.services;

import com.jp.springbootgradleredisweb.redis.strategies.DefaultStrategy;
import com.jp.springbootgradleredisweb.redis.strategies.RedisMemStrategyType;
import com.jp.springbootgradleredisweb.redis.strategies.RedisMemorizeStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;

@Service
@ConfigurationProperties
public class RedisService {
    @Resource
    private RedisTemplate redisTemplate;

    private RedisMemStrategyType currentType;
    private RedisMemorizeStrategy redisMemorizeStrategy;

    private RedisAtomicHandler redisAtomicHandler;

    @Value("serializer.customize")
    private String isSerializeCustomize;

    @PostConstruct
    private void init() {
        this.redisAtomicHandler = new RedisAtomicHandler(this.redisTemplate);
        this.redisMemorizeStrategy = new DefaultStrategy();
    }

    public void initRedisMemorizeStrategy(RedisMemStrategyType type) {
        if (isSerializeCustomize()) {
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
    }

    public Object get(String key) {
        return isSerializeCustomize() ? this.redisMemorizeStrategy.parseMemorizeBody(this.redisTemplate.opsForValue().get(key)) : this.redisTemplate.opsForValue().get(key);
    }

    public void set(String key, Object value) {
        this.redisTemplate.opsForValue().set(key, isSerializeCustomize() ? this.redisMemorizeStrategy.getMemorizeBody(value) : value);
    }

    public RedisLock getLock(String key, int expireSeconds) {
        return this.redisAtomicHandler.getLock(key, expireSeconds);
    }

    private boolean isSerializeCustomize() {
        return "true".equals(this.isSerializeCustomize);
    }
}
