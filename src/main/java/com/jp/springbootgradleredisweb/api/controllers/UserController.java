package com.jp.springbootgradleredisweb.api.controllers;

import com.jp.springbootgradleredisweb.api.bean.User;
import com.jp.springbootgradleredisweb.api.rest.JsonRes;
import com.jp.springbootgradleredisweb.api.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @GetMapping("/user/id/{id}")
    public JsonRes get(@PathVariable("id") String id) {
        if (StringUtils.hasText(id)) {
            try {
                User user = userService.getUserById(id);
                return new JsonRes("success", user);
            } catch (Exception e) {
                logger.info("[get error]:" + e.getMessage());
                return new JsonRes("fail:" + e.getMessage(), null);
            }
        }
        return new JsonRes("fail: id invalid !", null);
    }

    @GetMapping("/user")
    public JsonRes getAll() {
        String[] users = new String[]{"A", "B", "C"};
        return new JsonRes("success", users);
    }

    @PostMapping("/user")
    public JsonRes save(@RequestBody User user) {
        if (user != null && StringUtils.hasText(user.getName()) && StringUtils.hasText(user.getPassword())) {
            try {
                this.userService.saveUser(user);
                return new JsonRes("success", null);
            } catch (Exception e) {
                logger.info("[save error]:" + e.getMessage());
                return new JsonRes("fail:" + e.getMessage(), null);
            }
        }
        return new JsonRes("fail: user invalid !", null);
    }


}
