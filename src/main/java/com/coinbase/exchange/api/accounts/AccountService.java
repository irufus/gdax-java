package com.coinbase.exchange.api.accounts;

import com.coinbase.exchange.api.exchange.CoinbaseExchange;
import com.coinbase.exchange.api.exchange.GenericParameterizedType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.security.InvalidKeyException;

/**
 * Created by robevansuk on 25/01/2017.
 */
@Component
public class AccountService {

    @Autowired
    CoinbaseExchange exchange;

    @Autowired
    RestTemplate restTemplate;

    public static final String ACCOUNTS_ENDPOINT = "/accounts";

    public AccountHistory[] getAccountHistory(String accountid) throws CloneNotSupportedException, InvalidKeyException {
        String accountHistoryEndpoint = ACCOUNTS_ENDPOINT + "/" + accountid + "/ledger";
        return exchange.get(accountHistoryEndpoint, new GenericParameterizedType<AccountHistory[]>());
    }

    //todo ADD handler for request timestamp expired. This can be caused by an out of date clock
    public Account[] getAccounts() {
        String accountEndpoint = exchange.getBaseUrl() + ACCOUNTS_ENDPOINT;
        return exchange.get(accountEndpoint, new GenericParameterizedType<Account[]>());
    }
}
