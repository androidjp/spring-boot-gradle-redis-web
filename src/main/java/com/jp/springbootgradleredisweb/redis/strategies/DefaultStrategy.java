package com.jp.springbootgradleredisweb.redis.strategies;

import com.jp.springbootgradleredisweb.api.bean.Shipment;

public class DefaultStrategy implements RedisMemorizeStrategy<Shipment, Shipment> {
    @Override
    public Shipment getMemorizeBody(Shipment obj) {
        return obj;
    }

    @Override
    public Shipment parseMemorizeBody(Shipment memorizeBody) {
        return memorizeBody;
    }
}
