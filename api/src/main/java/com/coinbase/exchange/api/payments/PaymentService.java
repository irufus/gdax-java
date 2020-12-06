package com.coinbase.exchange.api.payments;

import com.coinbase.exchange.api.exchange.CoinbaseExchange;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

@Data
public class PaymentService {

    private static final String PAYMENT_METHODS_ENDPOINT = "/payment-methods";
    private static final String COINBASE_ACCOUNTS_ENDPOINT = "/coinbase-accounts";

    private final CoinbaseExchange coinbaseExchange;

    public List<PaymentType> getPaymentTypes() {
        return coinbaseExchange.getAsList(PAYMENT_METHODS_ENDPOINT, new ParameterizedTypeReference<>() {
        });
    }

    public List<CoinbaseAccount> getCoinbaseAccounts() {
        return coinbaseExchange.getAsList(COINBASE_ACCOUNTS_ENDPOINT, new ParameterizedTypeReference<>() {
        });
    }
}