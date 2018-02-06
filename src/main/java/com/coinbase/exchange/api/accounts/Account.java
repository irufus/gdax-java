package com.coinbase.exchange.api.accounts;

import com.coinbase.exchange.api.entity.Hold;

import java.math.BigDecimal;

/**
 * Created by irufus on 2/18/15.
 */
public class Account {
    private String id;
    private String currency;
    private BigDecimal balance;
    private BigDecimal available;
    private BigDecimal hold;
    private String profile_id;

    public Account() {
        this.balance = BigDecimal.ZERO;
        this.available = BigDecimal.ZERO;
        this.hold = BigDecimal.ZERO;
    }

    public Account(String id,
                   String currency,
                   BigDecimal balance,
                   BigDecimal available,
                   BigDecimal hold,
                   String profile_id) {
        this.id = id;
        this.currency = currency;
        this.balance = balance;
        this.available = available;
        this.hold = hold;
        this.profile_id = profile_id;
    }

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

    public String getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
    }
}
