package com.coinbase.exchange.api.orders;

import com.coinbase.exchange.api.BaseTest;
import com.coinbase.exchange.api.accounts.Account;
import com.coinbase.exchange.api.accounts.AccountService;
import com.coinbase.exchange.api.entity.Product;
import com.coinbase.exchange.api.marketdata.MarketDataService;
import com.coinbase.exchange.api.products.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static com.sun.tools.internal.ws.wsdl.parser.Util.fail;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertTrue;

/**
 * Created by Ishmael (sakamura@gmail.com) on 6/18/2016.
 */
public class OrderTests extends BaseTest {

    @Autowired
    ProductService productService;

    @Autowired
    AccountService accountService;

    @Autowired
    MarketDataService marketDataService;

    @Test
    public void canMakeOrder(){
        try {
            Product[] products = productService.getProducts();
            Account[] accounts = accountService.getAccounts();
            OrderBuilder builder = new OrderBuilder();

            String result = "";
            for(Product product : products){
                Order order = builder.setProduct_id(product.getId())
                        .setSize(new BigDecimal(1.0))
                        .setSide("BUY")
                        .build();
                result = marketDataService.getMarketDataOrderBook(product.getId(), "1");
                assertTrue(result != null);
            }
            // assert something rather than nothing here.
        } catch(Exception ex) {
            ex.printStackTrace();
            Assert.fail();
        }
    }


}
