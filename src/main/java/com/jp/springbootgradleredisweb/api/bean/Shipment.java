package com.jp.springbootgradleredisweb.api.bean;

import java.io.Serializable;
import java.util.List;

public class Shipment implements Serializable {
    private String shipmentId;
    private List<Route> relativeRoutes;

    public Shipment() {
    }

    public String getShipmentId() {
        return this.shipmentId;
    }

    public List<Route> getRelativeRoutes() {
        return this.relativeRoutes;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public void setRelativeRoutes(List<Route> relativeRoutes) {
        this.relativeRoutes = relativeRoutes;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Shipment)) return false;
        final Shipment other = (Shipment) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$shipmentId = this.shipmentId;
        final Object other$shipmentId = other.shipmentId;
        if (this$shipmentId == null ? other$shipmentId != null : !this$shipmentId.equals(other$shipmentId))
            return false;
        final Object this$relativeRoutes = this.relativeRoutes;
        final Object other$relativeRoutes = other.relativeRoutes;
        if (this$relativeRoutes == null ? other$relativeRoutes != null : !this$relativeRoutes.equals(other$relativeRoutes))
            return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $shipmentId = this.shipmentId;
        result = result * PRIME + ($shipmentId == null ? 43 : $shipmentId.hashCode());
        final Object $relativeRoutes = this.relativeRoutes;
        result = result * PRIME + ($relativeRoutes == null ? 43 : $relativeRoutes.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof Shipment;
    }

    public String toString() {
        return "Shipment(shipmentId=" + this.shipmentId + ", relativeRoutes=" + this.relativeRoutes + ")";
    }
}
