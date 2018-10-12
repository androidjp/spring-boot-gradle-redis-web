package com.jp.springbootgradleredisweb.api.services;

import com.jp.springbootgradleredisweb.api.bean.Shipment;

import java.util.List;

public interface ShipmentService {
    public List<Shipment> getShipments() throws Exception;
    public Shipment getShipmentById(String id) throws Exception;
    public void saveShipment(Shipment shipment) throws Exception;
}
