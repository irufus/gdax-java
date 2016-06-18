package com.coinbase.exchange.api.test;


import com.coinbase.exchange.api.CoinbaseExchange;
import com.coinbase.exchange.api.CoinbaseExchangeBuilder;
import com.coinbase.exchange.api.entity.Account;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by irufus (sakamura@gmail.com)
 * @Description The primary function of this class is to run through basic tests for the Authentication and CoinbaseExchange classes
 */
public class AuthenticationTests {
    @BeforeClass
    public static void oneTimeSetup(){
        System.out.println("Init General Tests \\==/ Authentication Tests");
    }
    @AfterClass
    public static void oneTimeTearDown(){
        System.out.println("Clean up \\==/ Authentication Tests");
    }

    @Test
    public void simpleAuthenticationTest(){
        String api = "";
        String key = "";
        String secret = "";
        String passphrase = "";
        try {
            CoinbaseExchange exchange = new CoinbaseExchangeBuilder()
                    .withAPIUrl(api)
                    .withAPIKeyAndPassphrase(key, secret, passphrase)
                    .build();
            Account[] accounts = exchange.getAccounts();
            assertTrue(accounts.length > 0);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

}
