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
    public static final String DEPOSIT_ENDPOINT = "/deposits";
    public static final String PAYMENTS = "/payment-method";
    public static final String COINBASE_PAYMENT = "/coinbase-account";
    public static final String PRODUCT_ENDPOINT = "/products";
    public static final String ORDERS_ENDPOINT = "/orders";
    public static final String PAYMENT_METHODS_ENDPOINT = "/payment-methods";
    public static final String COINBASE_ACCOUNTS_ENDPOINT = "/coinbase-accounts";
    public static final String PRODUCTS_ENDPOINT = "/products";
    public static final String REPORTS_ENDPOINT = "/reports";
    public static final String TRANSFER_ENDPOINT = "/transfers";
    public static final String USER_ACCOUNT_ENDPOINT = "/users/self/trailing-volume";
    public static final String WITHDRAWALS_ENDPOINT = "/withdrawals";
    public static final String PAYMENT_METHOD = "/payment-method";
    public static final String COINBASE = "/coinbase";
    public static final String CRYPTO = "/crypto";

    private GdaxStaticVariables() {
    }
}
