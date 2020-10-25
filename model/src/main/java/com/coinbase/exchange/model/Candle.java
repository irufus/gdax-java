package com.coinbase.exchange.model;

import java.math.BigDecimal;
import java.time.Instant;

public class Candle {

    private Instant time;
    private BigDecimal low;
    private BigDecimal high;
    private BigDecimal open;
    private BigDecimal close;
    private BigDecimal volume;

    public Candle(String[] entry) {
        this(Instant.ofEpochSecond(Long.parseLong(entry[0])),
                new BigDecimal(entry[1]),
                new BigDecimal(entry[2]),
                new BigDecimal(entry[3]),
                new BigDecimal(entry[4]),
                new BigDecimal(entry[5]));
    }

    public Candle(Instant time, BigDecimal low, BigDecimal high, BigDecimal open, BigDecimal close, BigDecimal volume) {
        this.time   = time;
        this.low    = low;
        this.high   = high;
        this.open   = open;
        this.close  = close;
        this.volume = volume;
    }

    public Instant getTime() {
        return time;
    }

    public BigDecimal getLow() {
        return low;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public BigDecimal getClose() {
        return close;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public void setClose(BigDecimal close) {
        this.close = close;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }
}
