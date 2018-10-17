package com.jp.springbootgradleredisweb.redis.strategies;

import com.jp.springbootgradleredisweb.api.bean.User;

public class DefaultUserStrategy implements RedisMemorizeStrategy<User, User> {
    @Override
    public User getMemorizeBody(User obj) {
        return obj;
    }

    @Override
    public User parseMemorizeBody(User memorizeBody) {
        return memorizeBody;
    }
}
