package com.jp.springbootgradleredisweb.redis.strategies;

import com.jp.springbootgradleredisweb.api.bean.Shipment;
import com.jp.springbootgradleredisweb.utils.FastjsonUtils;

public class FastjsonStrategy implements RedisMemorizeStrategy<Shipment, String> {

    @Override
    public String getMemorizeBody(Shipment obj) {
        return FastjsonUtils.objectToJson(obj);
    }

    @Override
    public Shipment parseMemorizeBody(String memorizeBody) {
        return FastjsonUtils.jsonToObject(memorizeBody, Shipment.class);
    }
}
