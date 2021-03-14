package com.coinbase.exchange.api.payments;

import com.coinbase.exchange.api.exchange.CoinbaseExchange;
import com.coinbase.exchange.api.exchange.CoinbaseExchangeException;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

/**
 * Created by robevansuk on 16/02/2017.
 */
public class PaymentService {

    private static final String PAYMENT_METHODS_ENDPOINT = "/payment-methods";
    private static final String COINBASE_ACCOUNTS_ENDPOINT = "/coinbase-accounts";

    final CoinbaseExchange coinbaseExchange;

    public PaymentService(final CoinbaseExchange coinbaseExchange) {
        this.coinbaseExchange = coinbaseExchange;
    }

    public List<PaymentType> getPaymentTypes() throws CoinbaseExchangeException {
        return coinbaseExchange.getAsList(PAYMENT_METHODS_ENDPOINT, new ParameterizedTypeReference<PaymentType[]>(){});
    }

    public List<CoinbaseAccount> getCoinbaseAccounts() throws CoinbaseExchangeException {
        return coinbaseExchange.getAsList(COINBASE_ACCOUNTS_ENDPOINT, new ParameterizedTypeReference<CoinbaseAccount[]>() {});
    }
}
