package com.coinbase.exchange.api.accounts;

import com.coinbase.exchange.api.exchange.CoinbaseExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

/**
 * Created by robevansuk on 25/01/2017.
 */
@Component
public class AccountService {

    @Autowired
    CoinbaseExchange exchange;

    public static final String ACCOUNTS_ENDPOINT = "/accounts";

    public AccountHistory[] getAccountHistory(String accountid) {
        String accountHistoryEndpoint = ACCOUNTS_ENDPOINT + "/" + accountid + "/ledger";
        return exchange.get(accountHistoryEndpoint, new ParameterizedTypeReference<AccountHistory[]>(){});
    }

    //todo ADD handler for request timestamp expired. This can be caused by an out of date clock
    public Account[] getAccounts(){
        return exchange.get(ACCOUNTS_ENDPOINT, new ParameterizedTypeReference<Account[]>(){});
    }
}
