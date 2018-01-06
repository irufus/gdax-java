package com.coinbase.exchange.api.useraccount;

import com.coinbase.exchange.api.exchange.GdaxExchange;

/**
 * Created by robevansuk on 17/02/2017.
 */
public class UserAccountService {

    static final String USER_ACCOUNT_ENDPOINT = "/users/self/trailing-volume";


    GdaxExchange gdaxExchange;

    public UserAccountData getUserAccounts(){
        return gdaxExchange.get(USER_ACCOUNT_ENDPOINT, UserAccountData.class);
    }
}
