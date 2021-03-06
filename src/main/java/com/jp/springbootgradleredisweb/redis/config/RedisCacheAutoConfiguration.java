package com.jp.springbootgradleredisweb.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;
import java.time.Duration;

@Configuration
@EnableCaching
public class RedisCacheAutoConfiguration extends CachingConfigurerSupport {

    @Resource
    JedisConfig jedisConfig;
    @Resource
    JedisConnectionFactory jedisConnectionFactory;


    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration rf = new RedisStandaloneConfiguration();
        rf.setDatabase(jedisConfig.database);
        rf.setHostName(jedisConfig.host);
        rf.setPort(jedisConfig.port);
        int to = Integer.parseInt(jedisConfig.timeout.substring(0, jedisConfig.timeout.length() - 2));
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpb =
                (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(jedisConfig.maxIdle);
        jedisPoolConfig.setMinIdle(jedisConfig.minIdle);
        jedisPoolConfig.setMaxTotal(jedisConfig.maxActive);
        int l = Integer.parseInt(jedisConfig.maxWait.substring(0, jedisConfig.maxWait.length() - 2));
        jedisPoolConfig.setMaxWaitMillis(l);
        jpb.poolConfig(jedisPoolConfig);
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(rf, jpb.build());
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate redisTemplate() {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        RedisTemplate rt = new RedisTemplate();
        rt.setConnectionFactory(jedisConnectionFactory);
        RedisSerializer rs = new StringRedisSerializer();
        rt.setKeySerializer(rs);
//        rt.setValueSerializer(jackson2JsonRedisSerializer);
        rt.setHashKeySerializer(rs);
//        rt.setHashValueSerializer(jackson2JsonRedisSerializer);
        rt.afterPropertiesSet();
        return rt;
    }

    /**
     * 以下是Springboot 1.x 的方式
     * 使用cache注解，管理redis 缓存
     * @return
     */
//    @Bean
//    public RedisCacheManager cacheManager() {
//        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate());
//        return redisCacheManager;
//    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(1)); // 设置缓存有效期一小时
        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration).build();
    }

}
