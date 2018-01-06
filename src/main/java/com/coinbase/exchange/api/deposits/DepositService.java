package com.coinbase.exchange.api.deposits;

import com.coinbase.exchange.api.config.GdaxStaticVariables;
import com.coinbase.exchange.api.entity.CoinbasePaymentRequest;
import com.coinbase.exchange.api.entity.PaymentResponse;
import com.coinbase.exchange.api.exchange.GdaxExchange;
import com.google.gson.Gson;

import java.math.BigDecimal;

/**
 * Created by robevansuk on 16/02/2017.
 */
public class DepositService {

    GdaxExchange exchange;

    /**
     * can't realistically test this but it should work as is.
     * @param amount
     * @param currency
     * @param paymentMethodId
     * @return
     */
    public PaymentResponse depositViaPaymentMethod(BigDecimal amount, String currency, String paymentMethodId) {
        String coinbasePaymentRequest = new Gson().toJson(new CoinbasePaymentRequest(amount, currency, paymentMethodId));
        return exchange.post(GdaxStaticVariables.DEPOSIT_ENDPOINT + GdaxStaticVariables.PAYMENTS,
                PaymentResponse.class,
                coinbasePaymentRequest);
    }

    /**
     * can't realistically test this but it should work as is.
     * @param amount
     * @param currency
     * @param coinbaseAccountId
     * @return
     */
    public PaymentResponse coinbaseDeposit(BigDecimal amount, String currency, String coinbaseAccountId) {
        String coinbasePaymentRequest = new Gson().toJson(new CoinbasePaymentRequest(amount, currency, coinbaseAccountId));
        return exchange.post(GdaxStaticVariables.DEPOSIT_ENDPOINT + GdaxStaticVariables.COINBASE_PAYMENT,
                PaymentResponse.class,
                coinbasePaymentRequest);
    }
}
