package com.coinbase.exchange.api.accounts;

import java.math.BigDecimal;

/**
 * Created by irufus on 2/18/15.
 */
public class Account {
    private String id;
    private BigDecimal balance;
    private BigDecimal hold;
    private BigDecimal available;
    private String currency;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getHold() {
        return hold;
    }

    public void setHold(BigDecimal hold) {
        this.hold = hold;
    }

    public BigDecimal getAvailable() {
        return available;
    }

    public void setAvailable(BigDecimal available) {
        this.available = available;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
