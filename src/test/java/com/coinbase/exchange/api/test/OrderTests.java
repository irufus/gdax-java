package com.coinbase.exchange.api.test;

import com.coinbase.exchange.api.CoinbaseExchange;
import com.coinbase.exchange.api.CoinbaseExchangeBuilder;
import com.coinbase.exchange.api.CoinbaseExchangeImpl;
import com.coinbase.exchange.api.entity.Account;
import com.coinbase.exchange.api.entity.Order;
import com.coinbase.exchange.api.entity.Product;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import static junit.framework.TestCase.fail;

/**
 * Created by Ishmael (sakamura@gmail.com) on 6/18/2016.
 */
public class OrderTests {
    static CoinbaseExchange exchange;
    @BeforeClass
    public static void oneTimeSetup(){
        Properties prop = new Properties();
        InputStream in = OrderTests.class.getClassLoader().getResourceAsStream("gdax.properties");
        try {
            prop.load(in);
            System.out.println("Init General Tests | Order Tests");
            exchange = new CoinbaseExchangeBuilder()
                    .withAPIUrl(prop.getProperty("gdax.api"))
                    .withAPIKeyAndPassphrase(prop.getProperty("gdax.key"), prop.getProperty("gdax.secret"), prop.getProperty("gdax.passphrase"))
                    .build();
        } catch(IOException ex){
            ex.printStackTrace();
            fail("Unable to read properties file");
        } catch(NoSuchAlgorithmException nex){
            nex.printStackTrace();
            fail("Algorithm not supported");
        }

    }
    @AfterClass
    public static void oneTimeTearDown(){
        System.out.println("Clean up | Order Tests");
    }

    @Test
    public void simpleMarketOrderTest(){
        try
        {
            Product[] products = exchange.getProducts();
            Account[] accounts = exchange.getAccounts();
            for(Product product : products){
                Order newOrder = new Order();
                //Order order = exchange.createOrder();
            }


        }
        catch(Exception ex){
            ex.printStackTrace();
            fail("simpleMarketOrderTest FAIL");
        }



    }
}
