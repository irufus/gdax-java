package com.coinbase.exchange.api.deposits;

import com.coinbase.exchange.api.exchange.GdaxExchange;
import com.coinbase.exchange.api.entity.CoinbasePaymentRequest;
import com.coinbase.exchange.api.entity.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Created by robevansuk on 16/02/2017.
 */
@Component
public class DepositService {

    static final String DEPOSIT_ENDPOINT = "/deposits";
    static final String PAYMENTS = "/payment-method";
    static final String COINBASE_PAYMENT = "/coinbase-account";

    @Autowired
    GdaxExchange exchange;

    /**
     * can't realistically test this but it should work as is.
     * @param amount
     * @param currency
     * @param paymentMethodId
     * @return
     */
    public PaymentResponse depositViaPaymentMethod(BigDecimal amount, String currency, String paymentMethodId) {
        CoinbasePaymentRequest coinbasePaymentRequest = new CoinbasePaymentRequest(amount, currency, paymentMethodId);
        return exchange.post(DEPOSIT_ENDPOINT + PAYMENTS,
                new ParameterizedTypeReference<PaymentResponse>(){},
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
        CoinbasePaymentRequest coinbasePaymentRequest = new CoinbasePaymentRequest(amount, currency, coinbaseAccountId);
        return exchange.post(DEPOSIT_ENDPOINT + COINBASE_PAYMENT,
                new ParameterizedTypeReference<PaymentResponse>(){},
                coinbasePaymentRequest);
    }
}
