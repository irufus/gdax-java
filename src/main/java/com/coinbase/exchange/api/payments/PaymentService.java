package com.coinbase.exchange.api.payments;

import com.coinbase.exchange.api.config.GdaxStaticVariables;
import com.coinbase.exchange.api.exchange.GdaxExchange;

/**
 * Created by robevansuk on 16/02/2017.
 */
public class PaymentService {

    GdaxExchange gdaxExchange;

    public PaymentTypes getPaymentTypes() {
        return gdaxExchange.get(GdaxStaticVariables.PAYMENT_METHODS_ENDPOINT, PaymentTypes.class);
    }

    public CoinbaseAccounts getCoinbaseAccounts() {
        return gdaxExchange.get(GdaxStaticVariables.COINBASE_ACCOUNTS_ENDPOINT, CoinbaseAccounts.class);
    }
}