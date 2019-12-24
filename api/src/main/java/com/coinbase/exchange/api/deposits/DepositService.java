package com.coinbase.exchange.api.deposits;

import com.coinbase.exchange.api.entity.CoinbasePaymentRequest;
import com.coinbase.exchange.api.entity.PaymentResponse;
import com.coinbase.exchange.api.exchange.CoinbaseExchange;
import org.springframework.core.ParameterizedTypeReference;

import java.math.BigDecimal;

/**
 * Created by robevansuk on 16/02/2017.
 */
public class DepositService {

    private static final String DEPOSIT_ENDPOINT = "/deposits";
    private static final String PAYMENTS = "/payment-method";
    private static final String COINBASE_PAYMENT = "/coinbase-account";

    final CoinbaseExchange exchange;

    public DepositService(final CoinbaseExchange exchange) {
        this.exchange = exchange;
    }

    /**
     * @param amount
     * @param currency
     * @param paymentMethodId
     * @return PaymentResponse
     */
    public PaymentResponse depositViaPaymentMethod(BigDecimal amount, String currency, final String paymentMethodId) {
        CoinbasePaymentRequest coinbasePaymentRequest = new CoinbasePaymentRequest(amount, currency, paymentMethodId);
        return exchange.post(DEPOSIT_ENDPOINT + PAYMENTS,
                new ParameterizedTypeReference<PaymentResponse>(){},
                coinbasePaymentRequest);
    }

    /**
     * @param amount
     * @param currency
     * @param coinbaseAccountId
     * @return PaymentResponse
     */
    public PaymentResponse depositViaCoinbase(BigDecimal amount, String currency, String coinbaseAccountId) {
        CoinbasePaymentRequest coinbasePaymentRequest = new CoinbasePaymentRequest(amount, currency, coinbaseAccountId);
        return exchange.post(DEPOSIT_ENDPOINT + COINBASE_PAYMENT,
                new ParameterizedTypeReference<PaymentResponse>(){},
                coinbasePaymentRequest);
    }
}
