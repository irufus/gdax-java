package com.coinbase.exchange.api.withdrawals;

import com.coinbase.exchange.api.entity.PaymentRequest;
import com.coinbase.exchange.api.entity.PaymentResponse;
import com.coinbase.exchange.api.exchange.GdaxExchange;
import com.google.gson.Gson;

import java.math.BigDecimal;

/**
 * Created by robevansuk on 16/02/2017.
 */

public class WithdrawalsService {

    static final String WITHDRAWALS_ENDPOINT = "/withdrawals";
    static final String PAYMENT_METHOD = "/payment-method";
    static final String COINBASE = "/coinbase";
    static final String CRYPTO = "/crypto";


    GdaxExchange gdaxExchange;

    // TODO untested - needs a payment method id.
    public PaymentResponse makeWithdrawalToPaymentMethod(BigDecimal amount, String currency, String paymentMethodId) {
        return makeWithdrawal(amount, currency, paymentMethodId, PAYMENT_METHOD);
    }

    // TODO untested - needs coinbase account ID to work.
    public PaymentResponse makeWithdrawalToCoinbase(BigDecimal amount, String currency, String paymentMethodId) {
        return makeWithdrawal(amount, currency, paymentMethodId, COINBASE);
    }

    // TODO untested - needs a crypto currency account address
    public PaymentResponse makeWithdrawalToCryptoAccount(BigDecimal amount, String currency, String cryptoAccount) {
        return makeWithdrawal(amount, currency, cryptoAccount, CRYPTO);
    }

    private PaymentResponse makeWithdrawal(BigDecimal amount,
                                           String currency,
                                           String cryptoAccount,
                                           String withdrawalType) {
        PaymentRequest withdrawalRequest = new PaymentRequest(amount, currency, cryptoAccount);
        Gson gson = new Gson();
        return gdaxExchange.post(WITHDRAWALS_ENDPOINT+ withdrawalType,
                PaymentResponse.class,
                gson.toJson(withdrawalRequest));
    }
}
