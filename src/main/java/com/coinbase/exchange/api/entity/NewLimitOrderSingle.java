package com.coinbase.exchange.api.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by irufus on 7/31/15.
 */
public class NewLimitOrderSingle extends NewOrderSingle{
    private BigDecimal price;
    private BigDecimal size;

    public NewLimitOrderSingle(){
        setType("limit");
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
}
