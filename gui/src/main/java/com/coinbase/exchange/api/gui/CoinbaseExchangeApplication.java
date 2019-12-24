package com.coinbase.exchange.api.gui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by robevansuk on 20/01/2017.
 */
@SpringBootApplication
public class CoinbaseExchangeApplication {
    public static final String SYSTEM_PROPERTY_JAVA_AWT_HEADLESS = "java.awt.headless";

    public static void main(String[] args) {
        System.setProperty(SYSTEM_PROPERTY_JAVA_AWT_HEADLESS, System.getProperty(SYSTEM_PROPERTY_JAVA_AWT_HEADLESS, Boolean.toString(false)));
        SpringApplication.run(CoinbaseExchangeApplication.class, args);
    }
}
