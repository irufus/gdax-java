package com.coinbase.exchange.model;

import java.math.BigInteger;

/**
 * Created by irufus on 2/25/15.
 */
public class Detail {
    private String order_id;
    private BigInteger trade_id;
    private String product_id;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public BigInteger getTrade_id() {
        return trade_id;
    }

    public void setTrade_id(BigInteger trade_id) {
        this.trade_id = trade_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }
}
