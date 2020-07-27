package com.coinbase.exchange.api.marketdata;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Created by robevansuk on 12/03/2017.
 */
public class Trade {
    Instant time;
    Long trade_id;
    BigDecimal price;
    BigDecimal size;
    String side;

    public Trade() {}

    public Trade(Instant time, Long trade_id, BigDecimal price, BigDecimal size, String side) {
        this.time = time;
        this.trade_id = trade_id;
        this.price = price;
        this.size = size;
        this.side = side;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public Long getTrade_id() {
        return trade_id;
    }

    public void setTrade_id(Long trade_id) {
        this.trade_id = trade_id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSize() {
        return size;
    }

    public void setSize(BigDecimal size) {
        this.size = size;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String toString(){
        return side.toUpperCase() + " " + size +"@" + price;
    }
}
