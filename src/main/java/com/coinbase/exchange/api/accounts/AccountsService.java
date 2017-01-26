package com.coinbase.exchange.api.accounts;

import com.coinbase.exchange.api.entity.Account;
import com.coinbase.exchange.api.exchange.CoinbaseExchange;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by robevansuk on 25/01/2017.
 */
@Component
public class AccountsService {

    @Autowired
    CoinbaseExchange exchange;

    public Account getAccount(String accountid) {
        return null;
    }

    public Account[] getAccounts() {
        String endpoint = "/accounts";
        String json = exchange.generateGetRequestJSON(endpoint); //todo ADD handler for request timestamp expired. This can be caused by an out of date clock
        Gson gson = new Gson();
        Account[] accounts = gson.fromJson(json, Account[].class );
        return accounts;
    }
}
