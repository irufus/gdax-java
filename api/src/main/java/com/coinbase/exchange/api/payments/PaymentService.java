package com.coinbase.exchange.api.payments;

import com.coinbase.exchange.api.exchange.CoinbasePro;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

/**
 * Created by robevansuk on 16/02/2017.
 */
public class PaymentService {

    private static final String PAYMENT_METHODS_ENDPOINT = "/payment-methods";
    private static final String COINBASE_ACCOUNTS_ENDPOINT = "/coinbase-accounts";

    final CoinbasePro coinbasePro;

    public PaymentService(final CoinbasePro coinbasePro) {
        this.coinbasePro = coinbasePro;
    }

    public List<PaymentType> getPaymentTypes() {
        return coinbasePro.getAsList(PAYMENT_METHODS_ENDPOINT, new ParameterizedTypeReference<PaymentType[]>(){});
    }

    public List<CoinbaseAccount> getCoinbaseAccounts() {
        return coinbasePro.getAsList(COINBASE_ACCOUNTS_ENDPOINT, new ParameterizedTypeReference<CoinbaseAccount[]>() {});
    }
}