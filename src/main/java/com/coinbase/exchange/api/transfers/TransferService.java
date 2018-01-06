package com.coinbase.exchange.api.transfers;

import com.coinbase.exchange.api.config.GdaxStaticVariables;
import com.coinbase.exchange.api.exchange.GdaxExchange;
import com.google.gson.Gson;

import java.math.BigDecimal;

/**
 * This class is best used in conjunction with the coinbase library
 * to get the coinbase account Id's. see: https://github.com/coinbase/coinbase-java
 *
 * Created by robevansuk on 15/02/2017.
 */

public class TransferService {

    GdaxExchange gdaxExchange;

    /**
     * TODO: untested due to lack of a coinbaseaccountID to test with.
     * @param type
     * @param amount
     * @param coinbaseAccountId
     * @return
     */
    public String transfer(String type, BigDecimal amount, String coinbaseAccountId) {
        Gson gson = new Gson();
        return gdaxExchange.post(GdaxStaticVariables.TRANSFER_ENDPOINT,
                String.class,
                gson.toJson(new Transfer(type, amount, coinbaseAccountId)));
    }

}
