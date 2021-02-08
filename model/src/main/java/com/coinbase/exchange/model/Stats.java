package com.coinbase.exchange.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Stats {
    private String productId;
    private BigDecimal volume30Day;
    private BigDecimal open24Hour;
    private BigDecimal high24Hour;
    private BigDecimal low24Hour;
    private BigDecimal volume24Hour;
    private BigDecimal last24Hour;

    @JsonProperty("stats_30day")
    private void stats30Day(Map<String, BigDecimal> volume30Day) {
        this.volume30Day = volume30Day.get("volume");
    }

    @JsonProperty("stats_24hour")
    private void stats24Hour(Map<String, BigDecimal> stats24Hour) {
        this.open24Hour = stats24Hour.get("open");
        this.high24Hour = stats24Hour.get("high");
        this.low24Hour = stats24Hour.get("low");
        this.volume24Hour = stats24Hour.get("volume");
        this.last24Hour = stats24Hour.get("last");
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public BigDecimal getVolume30Day() {
        return volume30Day;
    }

    public void setVolume30Day(BigDecimal volume30Day) {
        this.volume30Day = volume30Day;
    }

    public BigDecimal getOpen24Hour() {
        return open24Hour;
    }

    public void setOpen24Hour(BigDecimal open24Hour) {
        this.open24Hour = open24Hour;
    }

    public BigDecimal getHigh24Hour() {
        return high24Hour;
    }

    public void setHigh24Hour(BigDecimal high24Hour) {
        this.high24Hour = high24Hour;
    }

    public BigDecimal getLow24Hour() {
        return low24Hour;
    }

    public void setLow24Hour(BigDecimal low24Hour) {
        this.low24Hour = low24Hour;
    }

    public BigDecimal getVolume24Hour() {
        return volume24Hour;
    }

    public void setVolume24Hour(BigDecimal volume24Hour) {
        this.volume24Hour = volume24Hour;
    }

    public BigDecimal getLast24Hour() {
        return last24Hour;
    }

    public void setLast24Hour(BigDecimal last24Hour) {
        this.last24Hour = last24Hour;
    }
}
