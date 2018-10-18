package com.jp.springbootgradleredisweb.api.services;

import com.jp.springbootgradleredisweb.api.bean.User;
import com.jp.springbootgradleredisweb.redis.services.RedisLock;
import com.jp.springbootgradleredisweb.redis.services.RedisService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private RedisService redisService;

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
        try (RedisLock lock = redisService.getLock("userLock", 1)) {
            if (lock != null) {
                this.incrAge(id);
            }
        }
    }
}
