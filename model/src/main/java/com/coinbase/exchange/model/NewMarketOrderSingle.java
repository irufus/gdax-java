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

}
