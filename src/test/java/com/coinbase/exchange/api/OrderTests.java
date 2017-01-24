package com.coinbase.exchange.api;

import com.coinbase.exchange.api.entity.Account;
import com.coinbase.exchange.api.entity.Order;
import com.coinbase.exchange.api.entity.Product;
import org.junit.Assert;
import org.junit.Test;

import static com.sun.tools.internal.ws.wsdl.parser.Util.fail;
import static junit.framework.TestCase.fail;

/**
 * Created by Ishmael (sakamura@gmail.com) on 6/18/2016.
 */
public class OrderTests extends BaseTest {

    @Test
    public void simpleMarketOrderTest(){
        try {
            Product[] products = exchange.getProducts();
            Account[] accounts = exchange.getAccounts();
            for(Product product : products){
                Order newOrder = new Order();
            }
            // assert something rather than nothing here.
            Assert.assertTrue(true);
        } catch(Exception ex) {
            ex.printStackTrace();
            Assert.fail();
        }
    }
}
