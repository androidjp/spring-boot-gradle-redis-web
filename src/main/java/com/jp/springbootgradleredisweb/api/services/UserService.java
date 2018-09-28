package com.jp.springbootgradleredisweb.api.services;

import com.jp.springbootgradleredisweb.api.bean.User;

public interface UserService {
    void saveUser(User user) throws Exception;
    User getUserById(String id) throws Exception;
}
