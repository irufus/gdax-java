package com.coinbase.exchange.api.entity;

import java.math.BigDecimal;

/**
 * Created by robevansuk on 15/02/2017.
 */
public class PaymentRequest {

    BigDecimal amount;
    String currency;
    String payment_method_id;

    public PaymentRequest(BigDecimal amount, String currency, String payment_method_id) {
        this.amount = amount;
        this.currency = currency;
        this.payment_method_id = payment_method_id;
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

    public String getPayment_method_id() {
        return payment_method_id;
    }

    public void setPayment_method_id(String payment_method_id) {
        this.payment_method_id = payment_method_id;
    }
}
