package com.jp.springbootgradleredisweb.api.services;

import com.jp.springbootgradleredisweb.api.bean.Shipment;
import com.jp.springbootgradleredisweb.redis.services.RedisService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ShipmentServiceImpl implements ShipmentService {

    @Resource
    private RedisService redisService;

    @Override
    public List<Shipment> getShipments() throws Exception {
        return null;
    }

    @Override
    public Shipment getShipmentById(String id) throws Exception {
        return (Shipment) this.redisService.get("shipment:"+ id);
    }

    @Override
    public void saveShipment(Shipment shipment) throws Exception {
        this.redisService.set("shipment:"+ shipment.getShipmentId(), shipment);
    }
}
