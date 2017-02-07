package com.coinbase.exchange.api.accounts;

import com.coinbase.exchange.api.exchange.CoinbaseExchange;
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
public class AccountsService {

    @Autowired
    CoinbaseExchange exchange;

    @Autowired
    RestTemplate restTemplate;

    public static final String ACCOUNTS_ENDPOINT = "/accounts";

    public AccountHistory[] getAccountHistory(String accountid) throws CloneNotSupportedException, InvalidKeyException {
        String accountHistoryEndpoint = ACCOUNTS_ENDPOINT + "/" + accountid + "/ledger";
        ResponseEntity<AccountHistory[]> response = restTemplate.exchange( accountHistoryEndpoint,
                HttpMethod.GET,
                exchange.securityHeaders(accountHistoryEndpoint, "GET", ""),
                AccountHistory[].class);
        return response.getBody();
    }

    public Account[] getAccounts() throws CloneNotSupportedException, InvalidKeyException {
        String accountEndpoint = exchange.getBaseUrl() + "ACCOUNTS_ENDPOINT";
        //todo ADD handler for request timestamp expired. This can be caused by an out of date clock
        ResponseEntity<Account[]> accounts = restTemplate.exchange(accountEndpoint,
                HttpMethod.GET,
                exchange.securityHeaders(ACCOUNTS_ENDPOINT, "GET", ""),
                Account[].class);
        return accounts.getBody();
    }
}
