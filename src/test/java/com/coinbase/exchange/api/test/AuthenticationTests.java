package com.coinbase.exchange.api.test;


import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * Created by irufus (sakamura@gmail.com)
 * @Description: The primary function of this class is to run through basic tests for the Authentication and CoinbaseExchange classes
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

}
