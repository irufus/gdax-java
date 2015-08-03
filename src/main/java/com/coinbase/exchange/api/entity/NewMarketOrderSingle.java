package com.coinbase.exchange.api.entity;

import java.math.BigDecimal;

/**
 * Created by irufus on 7/31/15.
 */
public class NewMarketOrderSingle extends NewOrderSingle {
    private BigDecimal size; //optional: Desired amount in BTC
    private BigDecimal funds; //optional: Desired amount of fiat funds to use

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
    public BigDecimal getFunds() {
        return funds;
    }

    @Override
    public void setFunds(BigDecimal funds) {
        this.funds = funds;
    }
}
