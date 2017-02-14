package com.coinbase.exchange.api.marketdata;

import java.math.BigDecimal;

/**
 * Created by robevansuk on 09/02/2017.
 */
public class MarketData {

    Long sequence;
    BigDecimal[][] bids; // price, size, orders
    BigDecimal[][] asks; // price, size, orders

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
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
