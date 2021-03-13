package com.coinbase.exchange.api.exchange;

import org.springframework.web.client.HttpClientErrorException;

public class CoinbaseExchangeException extends Exception {
    CoinbaseExchangeException(final HttpClientErrorException cause) {
        super(cause);
    }
}
