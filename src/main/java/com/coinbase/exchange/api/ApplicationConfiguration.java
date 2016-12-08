package com.coinbase.exchange.api;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by robevansuk on 20/01/2017.
 */
@SpringBootConfiguration
public class ApplicationConfiguration {

    @Bean
    public Authentication authentication() {
        return new Authentication();
    }

    @Bean
    public CoinbaseExchange coinbaseExchange(Authentication auth) throws NoSuchAlgorithmException, MalformedURLException {
        CoinbaseExchangeBuilder builder = new CoinbaseExchangeBuilder();
        builder.withAuthentication(auth);

        return builder.build();
    }
}
