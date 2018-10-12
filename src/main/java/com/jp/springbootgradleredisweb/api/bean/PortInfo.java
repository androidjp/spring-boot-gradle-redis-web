package com.jp.springbootgradleredisweb.api.bean;

import java.io.Serializable;

public class PortInfo implements Serializable {
    private String prtId;
    private String prtName;
    private String eta;
    private String etd;
    private String ata;
    private String atd;

    public PortInfo() {
    }

    public String getPrtId() {
        return this.prtId;
    }

    public String getPrtName() {
        return this.prtName;
    }

    public String getEta() {
        return this.eta;
    }

    public String getEtd() {
        return this.etd;
    }

    public String getAta() {
        return this.ata;
    }

    public String getAtd() {
        return this.atd;
    }

    public void setPrtId(String prtId) {
        this.prtId = prtId;
    }

    public void setPrtName(String prtName) {
        this.prtName = prtName;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }

    public void setEtd(String etd) {
        this.etd = etd;
    }

    public void setAta(String ata) {
        this.ata = ata;
    }

    public void setAtd(String atd) {
        this.atd = atd;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof PortInfo)) return false;
        final PortInfo other = (PortInfo) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$prtId = this.prtId;
        final Object other$prtId = other.prtId;
        if (this$prtId == null ? other$prtId != null : !this$prtId.equals(other$prtId)) return false;
        final Object this$prtName = this.prtName;
        final Object other$prtName = other.prtName;
        if (this$prtName == null ? other$prtName != null : !this$prtName.equals(other$prtName)) return false;
        final Object this$eta = this.eta;
        final Object other$eta = other.eta;
        if (this$eta == null ? other$eta != null : !this$eta.equals(other$eta)) return false;
        final Object this$etd = this.etd;
        final Object other$etd = other.etd;
        if (this$etd == null ? other$etd != null : !this$etd.equals(other$etd)) return false;
        final Object this$ata = this.ata;
        final Object other$ata = other.ata;
        if (this$ata == null ? other$ata != null : !this$ata.equals(other$ata)) return false;
        final Object this$atd = this.atd;
        final Object other$atd = other.atd;
        if (this$atd == null ? other$atd != null : !this$atd.equals(other$atd)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $prtId = this.prtId;
        result = result * PRIME + ($prtId == null ? 43 : $prtId.hashCode());
        final Object $prtName = this.prtName;
        result = result * PRIME + ($prtName == null ? 43 : $prtName.hashCode());
        final Object $eta = this.eta;
        result = result * PRIME + ($eta == null ? 43 : $eta.hashCode());
        final Object $etd = this.etd;
        result = result * PRIME + ($etd == null ? 43 : $etd.hashCode());
        final Object $ata = this.ata;
        result = result * PRIME + ($ata == null ? 43 : $ata.hashCode());
        final Object $atd = this.atd;
        result = result * PRIME + ($atd == null ? 43 : $atd.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof PortInfo;
    }

    public String toString() {
        return "PortInfo(prtId=" + this.prtId + ", prtName=" + this.prtName + ", eta=" + this.eta + ", etd=" + this.etd + ", ata=" + this.ata + ", atd=" + this.atd + ")";
    }
}
