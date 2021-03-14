package com.coinbase.exchange.model;

import java.math.BigDecimal;

/**
 * Created by robevansuk on 15/02/2017.
 */
public class PaymentRequest extends MonetaryRequest {

    private String payment_method_id;

    public PaymentRequest(BigDecimal amount, String currency, String payment_method_id) {
        super(amount, currency);
        this.payment_method_id = payment_method_id;
    }

    public String getPayment_method_id() {
        return payment_method_id;
    }
    public void setPayment_method_id(String payment_method_id) {
        this.payment_method_id = payment_method_id;
    }

    @Override
    public String toString() {
        return "PaymentRequest{"
                + "amount=" + amount
                + ", currency='" + currency + '\''
                + ", payment_method_id='" + payment_method_id + '\''
                + '}';
    }
}
