package com.coinbase.exchange.api.exchange;

import org.springframework.core.ParameterizedTypeReference;

/**
 * Created by robevansuk on 07/02/2017.
 *
 * This class enables us to specify a parameterized type whose class type we can
 * figure out at run time - this means we can use the same methods for
 * GET, DELETE, POST, etc, in the CoinbaseExchange to return different objects
 * determined at runtime. A bit hacky but makes simplifies the code greatly.
 */
public class GenericParameterizedType<T> extends ParameterizedTypeReference<T> {

    Class<T> type;

    @Override
    public Class<T> getType() {
        return type;
    }
}