package com.jp.springbootgradleredisweb.api.services;

import com.jp.springbootgradleredisweb.api.bean.User;
import com.jp.springbootgradleredisweb.redis.services.RedisLock;
import com.jp.springbootgradleredisweb.redis.services.RedisService;
import com.jp.springbootgradleredisweb.redis.strategies.RedisMemStrategyType;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private RedisService redisService;

    @PostConstruct
    private void initRedisMemType() {
        this.redisService.initRedisMemorizeStrategy(RedisMemStrategyType.DEFAULT_USER);
    }

    @Override
    public void saveUser(User user) throws Exception {
        redisService.set("user:" + user.getName(), user);
    }

    @Override
    public User getUserById(String id) throws Exception {
        return (User) redisService.get("user:" + id);
    }

    @Override
    public void incrAge(String id) throws Exception {
        User user = (User) redisService.get("user:" + id);
        user.setAge(user.getAge() + 1);
        redisService.set("user:" + id, user);
    }

    @Override
    public void incrAgeSafely(String id) throws Exception {
        RedisLock lock = redisService.getLock("userLock", 2);
        if (lock != null) {
           try {
               this.incrAge(id);
           } finally {
               lock.unlock();
           }
        }
//        try (RedisLock lock = redisService.getLock("userLock", 2)) {
//            if (lock != null) {
//                this.incrAge(id);
//            }
//        }
    }
}
