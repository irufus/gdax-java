package com.coinbase.exchange.api;

import com.coinbase.exchange.api.accounts.Account;
import com.coinbase.exchange.api.accounts.AccountService;
import org.springframework.boot.SpringApplication;

/**
 * Created by robevansuk on 20/01/2017.
 */

public class GdaxApiApplication {
    public static final String SYSTEM_PROPERTY_JAVA_AWT_HEADLESS = "java.awt.headless";

    public static void main(String[] args) {
        System.setProperty(SYSTEM_PROPERTY_JAVA_AWT_HEADLESS, System.getProperty(SYSTEM_PROPERTY_JAVA_AWT_HEADLESS, Boolean.toString(false)));
        SpringApplication.run(GdaxApiApplication.class, args);
    }
}
