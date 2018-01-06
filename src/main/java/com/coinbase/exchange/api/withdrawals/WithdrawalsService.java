package com.coinbase.exchange.api.withdrawals;

import com.coinbase.exchange.api.config.GdaxStaticVariables;
import com.coinbase.exchange.api.entity.PaymentRequest;
import com.coinbase.exchange.api.entity.PaymentResponse;
import com.coinbase.exchange.api.exchange.GdaxExchange;
import com.google.gson.Gson;

import java.math.BigDecimal;

/**
 * Created by robevansuk on 16/02/2017.
 */

public class WithdrawalsService {


    GdaxExchange gdaxExchange;

    // TODO untested - needs a payment method id.
    public PaymentResponse makeWithdrawalToPaymentMethod(BigDecimal amount, String currency, String paymentMethodId) {
        return makeWithdrawal(amount, currency, paymentMethodId, GdaxStaticVariables.PAYMENT_METHOD);
    }

    // TODO untested - needs coinbase account ID to work.
    public PaymentResponse makeWithdrawalToCoinbase(BigDecimal amount, String currency, String paymentMethodId) {
        return makeWithdrawal(amount, currency, paymentMethodId, GdaxStaticVariables.COINBASE);
    }

    // TODO untested - needs a crypto currency account address
    public PaymentResponse makeWithdrawalToCryptoAccount(BigDecimal amount, String currency, String cryptoAccount) {
        return makeWithdrawal(amount, currency, cryptoAccount, GdaxStaticVariables.CRYPTO);
    }

    private PaymentResponse makeWithdrawal(BigDecimal amount,
                                           String currency,
                                           String cryptoAccount,
                                           String withdrawalType) {
        PaymentRequest withdrawalRequest = new PaymentRequest(amount, currency, cryptoAccount);
        Gson gson = new Gson();
        return gdaxExchange.post(GdaxStaticVariables.WITHDRAWALS_ENDPOINT + withdrawalType,
                PaymentResponse.class,
                gson.toJson(withdrawalRequest));
    }
}
