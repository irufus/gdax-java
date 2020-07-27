package com.coinbase.exchange.api.marketdata;

import java.util.List;

/**
 * Created by robevansuk on 09/02/2017.
 */
public class MarketData {

    private Long sequence;
    private List<OrderItem> bids; // price, size, orders
    private List<OrderItem> asks; // price, size, orders

    public MarketData() { }

    public MarketData(Long sequence, List<OrderItem> bids, List<OrderItem> asks) {
        this.sequence = sequence;
        this.bids = bids;
        this.asks = asks;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public List<OrderItem> getBids() {
        return bids;
    }

    public void setBids(List<OrderItem> bids) {
        this.bids = bids;
    }

    public List<OrderItem> getAsks() {
        return asks;
    }

    public void setAsks(List<OrderItem> asks) {
        this.asks = asks;
    }
}
