package com.jp.springbootgradleredisweb.redis.strategies;

public enum RedisMemStrategyType {
    DEFAULT(DefaultStrategy.class),
    JSON(FastjsonStrategy.class),
    PROTOSTUFF(ProtostuffStrategy.class);

    private Class<? extends RedisMemorizeStrategy> clazz;

    private RedisMemStrategyType(Class<? extends RedisMemorizeStrategy> clazz) {
        this.clazz = clazz;
    }

    public Class getClazz() {
        return this.clazz;
    }
}
