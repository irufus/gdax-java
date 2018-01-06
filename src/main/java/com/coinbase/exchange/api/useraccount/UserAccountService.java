package com.coinbase.exchange.api.useraccount;

import com.coinbase.exchange.api.config.GdaxStaticVariables;
import com.coinbase.exchange.api.exchange.GdaxExchange;

/**
 * Created by robevansuk on 17/02/2017.
 */
public class UserAccountService {

    GdaxExchange gdaxExchange;

    public UserAccountData getUserAccounts(){
        return gdaxExchange.get(GdaxStaticVariables.USER_ACCOUNT_ENDPOINT, UserAccountData.class);
    }
}
