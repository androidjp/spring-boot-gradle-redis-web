package com.jp.springbootgradleredisweb.api.bean;

import java.io.Serializable;

public class Container implements Serializable {
    private String containerId;
    private String containerNumber;
    private String type;
    private String routeId;

    public Container() {
    }

    public String getContainerId() {
        return this.containerId;
    }

    public String getContainerNumber() {
        return this.containerNumber;
    }

    public String getType() {
        return this.type;
    }

    public String getRouteId() {
        return this.routeId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public void setContainerNumber(String containerNumber) {
        this.containerNumber = containerNumber;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Container)) return false;
        final Container other = (Container) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$containerId = this.containerId;
        final Object other$containerId = other.containerId;
        if (this$containerId == null ? other$containerId != null : !this$containerId.equals(other$containerId))
            return false;
        final Object this$containerNumber = this.containerNumber;
        final Object other$containerNumber = other.containerNumber;
        if (this$containerNumber == null ? other$containerNumber != null : !this$containerNumber.equals(other$containerNumber))
            return false;
        final Object this$type = this.type;
        final Object other$type = other.type;
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
        final Object this$routeId = this.routeId;
        final Object other$routeId = other.routeId;
        if (this$routeId == null ? other$routeId != null : !this$routeId.equals(other$routeId)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $containerId = this.containerId;
        result = result * PRIME + ($containerId == null ? 43 : $containerId.hashCode());
        final Object $containerNumber = this.containerNumber;
        result = result * PRIME + ($containerNumber == null ? 43 : $containerNumber.hashCode());
        final Object $type = this.type;
        result = result * PRIME + ($type == null ? 43 : $type.hashCode());
        final Object $routeId = this.routeId;
        result = result * PRIME + ($routeId == null ? 43 : $routeId.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof Container;
    }

    public String toString() {
        return "Container(containerId=" + this.containerId + ", containerNumber=" + this.containerNumber + ", type=" + this.type + ", routeId=" + this.routeId + ")";
    }
}
