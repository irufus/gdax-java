package com.coinbase.exchange.api;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by irufus on 2/25/15.
 */
public class CoinbaseExchangeBuilder {

    URI endpoint;
    Authentication authentication;
    
    public CoinbaseExchange build() throws NoSuchAlgorithmException, MalformedURLException {
        return new CoinbaseExchangeImpl(this);
    }

    public CoinbaseExchangeBuilder withAPIUrl(String endpoint) throws MalformedURLException, URISyntaxException {
        this.endpoint = new URI(endpoint);
        return this;
    }

    public CoinbaseExchangeBuilder withAuthentication(Authentication auth) {
        this.authentication = auth;
        return this;
    }

}
