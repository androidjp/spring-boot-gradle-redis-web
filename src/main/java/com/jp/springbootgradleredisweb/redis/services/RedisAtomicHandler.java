package com.jp.springbootgradleredisweb.redis.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class RedisAtomicHandler {
    private final Logger logger = LoggerFactory.getLogger(RedisAtomicHandler.class);

    private static final String INCR_BY_WITH_TIMEOUT = "local v;" +
            " v = redis.call('incrBy',KEYS[1],ARGV[1]);" +
            "if tonumber(v) == 1 then\n" +
            "    redis.call('expire',KEYS[1],ARGV[2])\n" +
            "end\n" +
            "return v";
    private static final String COMPARE_AND_DELETE =
            "if redis.call('get',KEYS[1]) == ARGV[1]\n" +
                    "then\n" +
                    "    return redis.call('del',KEYS[1])\n" +
                    "else\n" +
                    "    return 0\n" +
                    "end";

    private final RedisTemplate redisTemplate;
    private final RedisConnectionFactory connectionFactory;
    private final StringRedisTemplate stringRedisTemplate;

    public RedisAtomicHandler(RedisTemplate redisTemplate, RedisConnectionFactory connectionFactory) {
        this.redisTemplate = redisTemplate;
        this.connectionFactory = connectionFactory;
        this.stringRedisTemplate = new StringRedisTemplate();
        this.stringRedisTemplate.setConnectionFactory(redisTemplate.getConnectionFactory());
        this.stringRedisTemplate.afterPropertiesSet();
    }

    public Long getLong(String key) {
        try {
            String val = stringRedisTemplate.opsForValue().get(key);
            return Long.valueOf(val);
        } catch (Exception e) {
            logger.error(String.format("get long value for key `%s` error:", key), e);
            return null;
        }
    }

    /**
     * counter 计数器，支持设置失效时间，如果key不存在，调用此方法后，计数器为1
     *
     * @param key
     * @param delta    可为负数
     * @param timeout  缓存失效时间
     * @param timeUnit 失效时间的单位
     * @return
     */
    public Long incrBy(String key, long delta, long timeout, TimeUnit timeUnit) {
        List<String> keys = new ArrayList<>();
        keys.add(key);
        long timeoutSeconds = TimeUnit.SECONDS.convert(timeout, timeUnit);
        String[] args = new String[2];
        args[0] = String.valueOf(delta);
        args[1] = String.valueOf(timeoutSeconds);
        Object currentVal = stringRedisTemplate.execute(new DefaultRedisScript<>(INCR_BY_WITH_TIMEOUT, String.class), keys, args);
        return Long.valueOf((String) currentVal);
    }

    public RedisLock getLock(final String key, long expireSeconds) {
        return getLock(key, expireSeconds, 0, 0);
    }

    public RedisLock getLock(final String key, final long expireSeconds, int maxRetryTimes, long retryIntervalTimeMillis) {
//        final String value = "" + System.currentTimeMillis();
        final String value = UUID.randomUUID().toString();

        int maxTimes = maxRetryTimes + 1;
        for (int i = 0; i < maxTimes; i++) {
            /// setnx ， 看看能够set 成功

            String status = this.stringRedisTemplate.execute((RedisCallback<String>) connection -> {
                Jedis jedis = (Jedis) connection.getNativeConnection();
                return jedis.set(key, value, "NX", "EX", expireSeconds);
            });

//            Boolean status = this.stringRedisTemplate.opsForValue().setIfAbsent(key, value);
            /// 如果成功，说明抢到了锁
            if ("OK".equals(status)) {
//                this.stringRedisTemplate.opsForValue().set(key, value, expireSeconds, TimeUnit.SECONDS);
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
//            stringRedisTemplate.execute((RedisCallback<String>) connection -> {
//                RedisAsyncCommandsImpl commands = (RedisAsyncCommandsImpl) connection.getNativeConnection();
//                return "" + commands.del(key);
//            });
        }

        @Override
        public void close() throws Exception {
            this.unlock();
        }
    }

}
