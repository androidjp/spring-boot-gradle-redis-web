package com.jp.springbootgradleredisweb.api.controllers;

import com.jp.springbootgradleredisweb.api.bean.Shipment;
import com.jp.springbootgradleredisweb.api.rest.JsonRes;
import com.jp.springbootgradleredisweb.api.services.ShipmentService;
import com.jp.springbootgradleredisweb.redis.services.RedisService;
import com.jp.springbootgradleredisweb.redis.strategies.RedisMemStrategyType;
import com.jp.springbootgradleredisweb.utils.FastjsonUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class ShipmentController {

    @Resource
    private RedisService redisService;

    @Resource
    private ShipmentService shipmentService;

    @PostMapping("/shipment")
    public JsonRes save(@RequestHeader(value = "redisMemType", required = false) RedisMemStrategyType redisMemType, @RequestBody(required = false) String body) {
        System.out.println("[ShipmentController.save] redisMemType:" + redisMemType);
        Shipment shipment = FastjsonUtils.jsonToObject(body, Shipment.class);
        try {
            this.redisService.initRedisMemorizeStrategy(redisMemType);
            shipmentService.saveShipment(shipment);
        } catch (Exception e) {
            System.out.println("save error:" + e.getMessage());
        }
        return new JsonRes("success", null);
    }

    @GetMapping("/shipment/id/{id}")
    public JsonRes get(@RequestHeader(value = "redisMemType", required = false) RedisMemStrategyType redisMemType, @PathVariable("id") String id) {
        System.out.println("[ShipmentController.get] redisMemType:" + redisMemType);
        if (StringUtils.hasText(id)) {
            String message = "success";
            Shipment shipment = null;
            try {
                this.redisService.initRedisMemorizeStrategy(redisMemType);
                shipment = this.shipmentService.getShipmentById(id);
            } catch (Exception e) {
                message = "[ShipmentController] get error: " + e.getMessage();
                System.out.println(message);
            } finally {
                return new JsonRes(message, shipment);
            }
        } else {
            return new JsonRes("failed: empty input `id`", null);
        }
    }
}
