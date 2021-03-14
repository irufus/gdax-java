package com.coinbase.exchange.model;

import java.math.BigDecimal;

/**
 * Created by irufus on 7/31/15.
 */
public class NewMarketOrderSingle extends NewOrderSingle {

    private BigDecimal size; //optional: Desired amount in BTC

    public NewMarketOrderSingle(BigDecimal size) {
        this.size = size;
    }

    public NewMarketOrderSingle(){
        super.setType("market");
    }

    public BigDecimal getSize() {
        return size;
    }

    public void setSize(BigDecimal size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "NewMarketOrderSingle{"
                + "size=" + size
                + ", client_oid='" + client_oid + '\''
                + ", type='" + type + '\''
                + ", side='" + side + '\''
                + ", product_id='" + product_id + '\''
                + ", stp='" + stp + '\''
                + ", funds='" + funds + '\''
                + '}';
    }
}
