package com.coinbase.exchange.api.entity;

import java.math.BigDecimal;

/**
 * Created by irufus on 7/31/15.
 */
public class NewMarketOrderSingle extends NewOrderSingle {
    private BigDecimal size; //optional: Desired amount in BTC

    public NewMarketOrderSingle(){
        setType("market");
    }

    public BigDecimal getSize() {
        return size;
    }

    public void setSize(BigDecimal size) {
        this.size = size;
    }

    @Override
    public String toString()
    {
        return "NewMarketOrderSingle{" + "size=" + size +
               ", type='" +
               getType() +
               '\'' +
               ", side='" +
               getSide() +
               '\'' +
               ", product_id='" +
               getProduct_id() +
               '\'' +
               ", stp='" +
               getStp() +
               '\'' +
               ", funds='" +
               getFunds() +
               '\'' +
               '}';
    }
}
