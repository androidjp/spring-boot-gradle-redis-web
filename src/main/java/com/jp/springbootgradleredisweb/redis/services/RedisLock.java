package com.jp.springbootgradleredisweb.redis.services;

public interface RedisLock extends AutoCloseable {

    void unlock();
}
