package org.irufus.coinbaseexc.api;

import java.security.NoSuchAlgorithmException;

/**
 * Created by irufus on 2/25/15.
 */
public class CoinbaseExchangeBuilder {
    String public_key;
    String secret_key;
    String passphrase;
    String url;
    
    public CoinbaseExchange build() throws NoSuchAlgorithmException {
        return new CoinbaseExchangeImpl(this);
    }
    public CoinbaseExchangeBuilder withAPIUrl(String url)
    {
        this.url = url;
        return this;
    }
    public CoinbaseExchangeBuilder withAPIKeyAndPassphrase(String api_key, String api_secret, String passphrase)
    {
        this.secret_key = api_secret;
        this.public_key = api_key;
        this.passphrase = passphrase;
        return this;
    }

}
