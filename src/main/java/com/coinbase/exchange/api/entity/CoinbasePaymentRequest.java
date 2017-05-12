package com.coinbase.exchange.api.entity;

import java.math.BigDecimal;

/**
 * Created by robevansuk on 15/02/2017.
 */
public class CoinbasePaymentRequest {

    String currency;
    BigDecimal amount;
    String coinbase_account_id;

    public CoinbasePaymentRequest(BigDecimal amount, String currency, String coinbase_account_id) {
        this.currency = currency;
        this.amount = amount;
        this.coinbase_account_id = coinbase_account_id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCoinbase_account_id() {
        return coinbase_account_id;
    }

    public void setCoinbase_account_id(String coinbase_account_id) {
        this.coinbase_account_id = coinbase_account_id;
    }
}
