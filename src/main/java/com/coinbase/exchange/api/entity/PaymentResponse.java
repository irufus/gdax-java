package com.coinbase.exchange.api.entity;

import java.math.BigDecimal;

/**
 * Created by robevansuk on 15/02/2017.
 */
public class PaymentResponse {

    String id;
    BigDecimal amount;
    String currency;
    String payout_at;

    public PaymentResponse() {}

    public PaymentResponse(String id, BigDecimal amount, String currency, String payout_at) {
        this.id = id;
        this.amount = amount;
        this.currency = currency;
        this.payout_at = payout_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPayout_at() {
        return payout_at;
    }

    public void setPayout_at(String payout_at) {
        this.payout_at = payout_at;
    }
}
