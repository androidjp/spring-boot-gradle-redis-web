package com.jp.springbootgradleredisweb.api.bean;

import java.io.Serializable;
import java.util.List;

public class Route implements Serializable {
    private String routeId;
    private List<PortPair> ppList;

    public Route() {
    }

    public String getRouteId() {
        return this.routeId;
    }

    public List<PortPair> getPpList() {
        return this.ppList;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public void setPpList(List<PortPair> ppList) {
        this.ppList = ppList;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Route)) return false;
        final Route other = (Route) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$routeId = this.routeId;
        final Object other$routeId = other.routeId;
        if (this$routeId == null ? other$routeId != null : !this$routeId.equals(other$routeId)) return false;
        final Object this$ppList = this.ppList;
        final Object other$ppList = other.ppList;
        if (this$ppList == null ? other$ppList != null : !this$ppList.equals(other$ppList)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $routeId = this.routeId;
        result = result * PRIME + ($routeId == null ? 43 : $routeId.hashCode());
        final Object $ppList = this.ppList;
        result = result * PRIME + ($ppList == null ? 43 : $ppList.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof Route;
    }

    public String toString() {
        return "Route(routeId=" + this.routeId + ", ppList=" + this.ppList + ")";
    }
}
