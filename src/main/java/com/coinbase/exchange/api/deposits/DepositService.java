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

    private static final String DEPOSIT_ENDPOINT = "/deposits";
    private static final String PAYMENTS = "/payment-method";
    private static final String COINBASE_PAYMENT = "/coinbase-account";

    @Autowired
    GdaxExchange exchange;

    /**
     * @param amount
     * @param currency
     * @param paymentMethodId
     * @return PaymentResponse
     */
    public PaymentResponse depositViaPaymentMethod(BigDecimal amount, String currency, final String paymentMethodId) {
<<<<<<< HEAD
        CoinbasePaymentRequest coinbasePaymentRequest = new CoinbasePaymentRequest(amount, currency, paymentMethodId);
=======
        CoinbasePaymentRequest coinbasePaymentRequest = new MyCoinbasePaymentRequest(amount, currency, paymentMethodId);
>>>>>>> 24388b5a2ab33825e41b9814e17b426e13656260
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

    private static class MyCoinbasePaymentRequest extends CoinbasePaymentRequest {
        public final String payment_method_id;
        //public final BigDecimal amount;
       // private final String paymentMethodId;

        public MyCoinbasePaymentRequest(BigDecimal amount, String currency, String paymentMethodId) {
            super(amount, currency, paymentMethodId);
            //this.paymentMethodId = paymentMethodId;
            payment_method_id = paymentMethodId;
           // this.amount = amount;
        }
    }
}
