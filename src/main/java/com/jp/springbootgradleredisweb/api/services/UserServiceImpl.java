package com.jp.springbootgradleredisweb.api.services;

import com.jp.springbootgradleredisweb.api.bean.User;
import com.jp.springbootgradleredisweb.api.mappers.UserMapper;
import com.jp.springbootgradleredisweb.redis.services.RedisLock;
import com.jp.springbootgradleredisweb.redis.services.RedisService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

@CacheConfig(cacheNames = "user")
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private RedisService redisService;
    @Resource
    private UserMapper userMapper;

    @CachePut(key="#user.id", unless="#result == null")
    @Override
    public User saveUser(User user) throws Exception {
        System.out.println("[saveUser] start....");
        if(user.getId() == null) {
            user.setId(UUID.randomUUID().toString());
            this.userMapper.insert(user);
        } else {
            this.userMapper.update(user);
        }
        return user;
    }

    @Cacheable(key = "#id", unless="#result == null")
    @Override
    public User getUserById(String id) throws Exception {
        System.out.println("[getUserById] start....");
        return userMapper.get(id);
    }

    @Override
    public void incrAge(String id) throws Exception {
        User user = (User) redisService.get("user:" + id);
        user.setAge(user.getAge() + 1);
        redisService.set("user:" + id, user);
    }

    @Override
    public void incrAgeSafely(String id) throws Exception {
        try (RedisLock lock = redisService.getLock("userLock", 1)) {
            if (lock != null) {
                this.incrAge(id);
            }
        }
    }
}
