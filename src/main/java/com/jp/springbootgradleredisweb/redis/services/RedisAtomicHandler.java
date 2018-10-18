package com.jp.springbootgradleredisweb.redis.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class RedisAtomicHandler {
    private static final int DEFAULT_RETRY_INTERVAL_TIME_MILLIS = 200;
    private static final int DEFUALT_MAX_RETRY_TIMES = 2;
    private final Logger logger = LoggerFactory.getLogger(RedisAtomicHandler.class);
    private static final String COMPARE_AND_DELETE =
            "if redis.call('get',KEYS[1]) == ARGV[1]\n" +
                    "then\n" +
                    "    return redis.call('del',KEYS[1])\n" +
                    "else\n" +
                    "    return 0\n" +
                    "end";

    private final RedisTemplate redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;

    public RedisAtomicHandler(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = new StringRedisTemplate();
        this.stringRedisTemplate.setConnectionFactory(redisTemplate.getConnectionFactory());
        this.stringRedisTemplate.afterPropertiesSet();
    }

    public RedisLock getLock(final String key, long expireSeconds) {
        return getLock(key, expireSeconds, DEFUALT_MAX_RETRY_TIMES, DEFAULT_RETRY_INTERVAL_TIME_MILLIS);
    }

    public RedisLock getLock(final String key, final long expireSeconds, int maxRetryTimes, long retryIntervalTimeMillis) {
        final String value = UUID.randomUUID().toString();
        int maxTimes = maxRetryTimes + 1;
        for (int i = 0; i < maxTimes; i++) {
            /// setnx ， 看看能够set 成功
            String status = this.stringRedisTemplate.execute((RedisCallback<String>) connection -> {
                Jedis jedis = (Jedis) connection.getNativeConnection();
                return jedis.set(key, value, "NX", "EX", expireSeconds);
            });
            /// 如果成功，说明抢到了锁
            if ("OK".equals(status)) {
                return new RedisLockInner(stringRedisTemplate, key, value);
            }
            // 每次抢不到锁，就让当前线程休眠 一段时间
            if (retryIntervalTimeMillis > 0) {
                try {
                    Thread.sleep(retryIntervalTimeMillis);
                } catch (InterruptedException e) {
                    break;
                }
            }
            if (Thread.currentThread().isInterrupted()) {
                break;
            }
        }
        return null;
    }

    private class RedisLockInner implements RedisLock {
        private StringRedisTemplate stringRedisTemplate;
        private String key;
        private String expectedValue;

        protected RedisLockInner(StringRedisTemplate stringRedisTemplate, String key, String expectedValue) {
            this.stringRedisTemplate = stringRedisTemplate;
            this.key = key;
            this.expectedValue = expectedValue;
        }

        /**
         * 释放redis分布式锁
         */
        @Override
        public void unlock() {
            List<String> keys = Collections.singletonList(key);
            stringRedisTemplate.execute(new DefaultRedisScript<>(COMPARE_AND_DELETE, String.class), keys, expectedValue);
        }

        @Override
        public void close() throws Exception {
            this.unlock();
        }
    }

}
