package com.jp.springbootgradleredisweb.api.bean;

import java.io.Serializable;

public class PortPair implements Serializable {
    private String id;
    private PortInfo pol;
    private PortInfo pod;
    private String routeId;
    private int seq;

    public PortPair() {
    }

    public PortInfo getPol() {
        return this.pol;
    }

    public PortInfo getPod() {
        return this.pod;
    }

    public String getRouteId() {
        return this.routeId;
    }

    public int getSeq() {
        return this.seq;
    }

    public void setPol(PortInfo pol) {
        this.pol = pol;
    }

    public void setPod(PortInfo pod) {
        this.pod = pod;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof PortPair)) return false;
        final PortPair other = (PortPair) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$pol = this.pol;
        final Object other$pol = other.pol;
        if (this$pol == null ? other$pol != null : !this$pol.equals(other$pol)) return false;
        final Object this$pod = this.pod;
        final Object other$pod = other.pod;
        if (this$pod == null ? other$pod != null : !this$pod.equals(other$pod)) return false;
        final Object this$routeId = this.routeId;
        final Object other$routeId = other.routeId;
        if (this$routeId == null ? other$routeId != null : !this$routeId.equals(other$routeId)) return false;
        if (this.seq != other.seq) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $pol = this.pol;
        result = result * PRIME + ($pol == null ? 43 : $pol.hashCode());
        final Object $pod = this.pod;
        result = result * PRIME + ($pod == null ? 43 : $pod.hashCode());
        final Object $routeId = this.routeId;
        result = result * PRIME + ($routeId == null ? 43 : $routeId.hashCode());
        result = result * PRIME + this.seq;
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof PortPair;
    }

    public String toString() {
        return "PortPair(pol=" + this.pol + ", pod=" + this.pod + ", routeId=" + this.routeId + ", seq=" + this.seq + ")";
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
