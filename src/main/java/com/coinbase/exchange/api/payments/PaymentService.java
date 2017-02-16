package com.coinbase.exchange.api.payments;

import com.coinbase.exchange.api.exchange.GdaxExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Created by robevansuk on 15/02/2017.
 */
@Component
public class PaymentService {

    static final String DEPOSIT_ENDPOINT = "/deposits";
    static final String PAYMENTS = "/payment-method";
    static final String COINBASE_PAYMENT = "/coinbase-account";

    @Autowired
    GdaxExchange gdaxExchange;

    public void getPaymentMethods() {

    }

    // TODO untested due to lack of a payment method Id.
    public PaymentResponse paymentDeposit(BigDecimal amount, String currency, String paymentMethodId) {
        PaymentRequest paymentRequest = new PaymentRequest(amount, currency, paymentMethodId);
        return gdaxExchange.post(DEPOSIT_ENDPOINT + PAYMENTS,
                new ParameterizedTypeReference<PaymentResponse>(){},
                paymentRequest);
    }

    // TODO untested due to lack of a coinbase account currency
    public PaymentResponse coinbaseDeposit(BigDecimal amount, String currency, String coinbaseAccointId) {
        CoinbasePaymentRequest coinbasePaymentRequest = new CoinbasePaymentRequest(amount, currency, coinbaseAccointId);
        return gdaxExchange.post(DEPOSIT_ENDPOINT + COINBASE_PAYMENT,
                new ParameterizedTypeReference<PaymentResponse>(){},
                coinbasePaymentRequest);
    }
}
