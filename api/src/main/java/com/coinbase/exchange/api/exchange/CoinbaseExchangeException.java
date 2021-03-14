package com.coinbase.exchange.api.exchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.HttpClientErrorException;

public class CoinbaseExchangeException extends Exception {
    public CoinbaseExchangeException(final String message, final Throwable cause) {
        super(message, cause);
    }

    static CoinbaseExchangeException create(final HttpClientErrorException cause, final ObjectMapper objectMapper) {
        String message = null;
        try {
            final Error error = objectMapper.readValue(cause.getResponseBodyAsString(), Error.class);
            message = error.getMessage();
        } catch (JsonProcessingException e) {
            // ignore
        }

        return new CoinbaseExchangeException(message, cause);
    }

    private static class Error {
        private String message;

        public String getMessage() {
            return message;
        }
    }
}
