package com.coinbase.exchange.api.payments;

import com.coinbase.exchange.api.exchange.GdaxExchange;

/**
 * Created by robevansuk on 16/02/2017.
 */
public class PaymentService {

    static final String PAYMENT_METHODS_ENDPOINT = "/payment-methods";
    static final String COINBASE_ACCOUNTS_ENDPOINT = "/coinbase-accounts";

    GdaxExchange gdaxExchange;

    public PaymentTypes getPaymentTypes() {
        return gdaxExchange.get(PAYMENT_METHODS_ENDPOINT, PaymentTypes.class);
    }

    public CoinbaseAccounts getCoinbaseAccounts() {
        return gdaxExchange.get(COINBASE_ACCOUNTS_ENDPOINT, CoinbaseAccounts.class);
    }
}