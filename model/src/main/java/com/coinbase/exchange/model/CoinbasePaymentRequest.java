package com.coinbase.exchange.model;

import java.math.BigDecimal;

/**
 * Created by robevansuk on 15/02/2017.
 */
public class CoinbasePaymentRequest extends MonetaryRequest {

    private String coinbase_account_id;
    private String payment_method_id;

    public CoinbasePaymentRequest(BigDecimal amount, String currency, String coinbase_account_id) {
        super(amount, currency);
        this.coinbase_account_id = coinbase_account_id;
        this.payment_method_id = coinbase_account_id; //Duplicated field for gdax compliance, I believe
        //We could probably remove coinbase_account_id but there are no tests for this specific thing
    }

    public String getCoinbase_account_id() {
        return coinbase_account_id;
    }

    public void setCoinbase_account_id(String coinbase_account_id) {
        this.coinbase_account_id = coinbase_account_id;
    }

    @Override
    public String toString() {
        return "CoinbasePaymentRequest{"
                + "coinbase_account_id='" + coinbase_account_id + '\''
                + ", payment_method_id='" + payment_method_id + '\''
                + ", amount=" + amount
                + ", currency='" + currency + '\''
                + '}';
    }
}
