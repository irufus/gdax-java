package com.coinbase.exchange.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main codebase is a library. In order to test it we can
 * instantiate a spring boot application that will wire in the various
 * config from application-test.yaml properties and connect to the exchange.
 * This makes running integration tests relatively simple.
 */
@SpringBootApplication
public class TestExchangeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestExchangeApplication.class, args);
    }

}
