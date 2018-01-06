package com.coinbase.exchange.api.config;

/**
 * Created by robevansuk on 07/02/2017.
 */

public final class GdaxStaticVariables {

    public static final String ACCOUNTS_ROOT = "/accounts";
    public static final String ACCOUNTS_LEDGER = "/ledger";
    public static final String ACCOUNTS_HOLDS = "/holds";
    public static final String SOCKET_URL = "wss://ws-feed.gdax.com/";
    public static final String REST_URL = "https://api.gdax.com/";

    private GdaxStaticVariables() {
    }
}
