package com.coinbase.exchange.api.marketdata;

import java.math.BigDecimal;

/**
 * Created by robevansuk on 11/02/2017.
 */
public class MarketData {

    Integer sequence;
    BigDecimal[][] bids; // price, size, orders
    BigDecimal[][] asks; // price, size, orders

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public BigDecimal[][] getBids() {
        return bids;
    }

    public void setBids(BigDecimal[][] bids) {
        this.bids = bids;
    }

    public BigDecimal[][] getAsks() {
        return asks;
    }

    public void setAsks(BigDecimal[][] asks) {
        this.asks = asks;
    }
}
