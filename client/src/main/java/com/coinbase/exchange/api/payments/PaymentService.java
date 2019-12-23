package com.coinbase.exchange.api.payments;

import com.coinbase.exchange.api.exchange.CoinbasePro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by robevansuk on 16/02/2017.
 */
@Component
public class PaymentService {

    private static final String PAYMENT_METHODS_ENDPOINT = "/payment-methods";
    private static final String COINBASE_ACCOUNTS_ENDPOINT = "/coinbase-accounts";

    @Autowired
    CoinbasePro coinbasePro;

    public List<PaymentType> getPaymentTypes() {
        return coinbasePro.getAsList(PAYMENT_METHODS_ENDPOINT, new ParameterizedTypeReference<PaymentType[]>(){});
    }

    public List<CoinbaseAccount> getCoinbaseAccounts() {
        return coinbasePro.getAsList(COINBASE_ACCOUNTS_ENDPOINT, new ParameterizedTypeReference<CoinbaseAccount[]>() {});
    }
}