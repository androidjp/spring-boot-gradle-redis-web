package com.jp.springbootgradleredisweb.api.services;

import com.jp.springbootgradleredisweb.api.bean.User;
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
}
