package com.jp.springbootgradleredisweb.redis.strategies;

import com.jp.springbootgradleredisweb.api.bean.Shipment;
import com.jp.springbootgradleredisweb.utils.ProtoBufUtils;

public class ProtostuffStrategy implements RedisMemorizeStrategy<Shipment,byte[]> {

    @Override
    public byte[] getMemorizeBody(Shipment obj) {
        return ProtoBufUtils.serializer(obj);
    }

    @Override
    public Shipment parseMemorizeBody(byte[] memorizeBody) {
        return ProtoBufUtils.deserializer(memorizeBody, Shipment.class);
    }
}
