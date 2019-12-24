package com.coinbase.exchange.api.payments;

import java.math.BigDecimal;

/**
 * Created by robevansuk on 16/02/2017.
 */
public class Amount {

    BigDecimal amount;
    String currency;

    public Amount() {}

    public Amount(BigDecimal amount, String currency) {
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
