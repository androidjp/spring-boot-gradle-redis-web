package com.jp.springbootgradleredisweb.redis.strategies;

public interface RedisMemorizeStrategy<T, S> {
    public S getMemorizeBody(T obj);
    public T parseMemorizeBody(S memorizeBody);
}
