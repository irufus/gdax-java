package com.coinbase.exchange.model;

import java.math.BigDecimal;

public abstract class MonetaryRequest {
    protected BigDecimal amount;
    protected String currency;

    public MonetaryRequest(BigDecimal amount, String currency){
        this.amount = amount;
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
