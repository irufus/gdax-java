package com.coinbase.exchange.api.orders;

import com.coinbase.exchange.api.BaseTest;
import com.coinbase.exchange.api.accounts.Account;
import com.coinbase.exchange.api.accounts.AccountService;
import com.coinbase.exchange.api.entity.NewLimitOrderSingle;
import com.coinbase.exchange.api.entity.Product;
import com.coinbase.exchange.api.marketdata.MarketData;
import com.coinbase.exchange.api.marketdata.MarketDataService;
import com.coinbase.exchange.api.products.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Arrays;

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

    @Autowired
    OrderService orderService;

    @Test
    public void canMakeLimitOrder(){
        try {
            Product btcUsdProduct = getProducts();
            assertTrue(btcUsdProduct.getId().equals("BTC-USD"));
            MarketData marketData = getMarketDataOrderBook(btcUsdProduct);
            assertTrue(marketData != null);
            Account usdAccount = getUsdAccount();

            NewLimitOrderSingle limitOrder = new NewLimitOrderSingle();
            limitOrder.setSide("buy");
            limitOrder.setProduct_id(btcUsdProduct.getId());
            limitOrder.setPrice(new BigDecimal(200.00));//getAskPrice(marketData));
            limitOrder.setSize(new BigDecimal(0.01)); //getSizeOfOrder(marketData));

            Order[] openOrders = orderService.getOpenOrders();
            Order order = orderService.createOrder(limitOrder);
            // assert something rather than nothing here.
            assertTrue(order.getId() != null);
        } catch(Exception ex) {
            ex.printStackTrace();
            Assert.fail();
        }
    }

    private Order[] getOrderIds() {
        return orderService.getOpenOrders();
    }

    private String getSizeOfOrder(MarketData marketData) {
        BigDecimal quantity = marketData.getAsks()[0][1].setScale(8, BigDecimal.ROUND_HALF_UP);
        BigDecimal size = new BigDecimal(0.001);
        size.setScale(8, BigDecimal.ROUND_HALF_UP);
        if (size.floatValue() > quantity.floatValue()) {
            size = quantity;
        }
        size = size.setScale(8, BigDecimal.ROUND_HALF_UP);
        return size + "";
    }

    private MarketData getMarketDataOrderBook(Product btcUsdProduct) {
        return marketDataService.getMarketDataOrderBook(btcUsdProduct.getId(), "1");
    }

    private String getAskPrice(MarketData marketData) {
        return marketData.getAsks()[0][0].setScale(8, BigDecimal.ROUND_HALF_UP) + "";
    }

    private Product getProducts() {
        Product[] products = productService.getProducts();
        Product product = Arrays.stream(products)
                .filter(p -> p.getId().equals("BTC-USD"))
                .findFirst()
                .orElseThrow(() -> new NullPointerException("BTC-USD product unavailable"));
        return product;
    }

    private Account getUsdAccount() throws Exception {
        Account[] accounts = accountService.getAccounts();
        Account usdAccount = Arrays.stream(accounts)
                .filter(account -> account.getCurrency().equals("USD"))
                .findFirst()
                .orElseThrow(() -> new NullPointerException("No USD accounts available"));
        if (usdAccount.getBalance().intValue() <= 1) {
            throw new Exception("Not enough funds in your USD account for the test transaction. " +
                    "Please top up your sandbox USD account.");
        }
        return usdAccount;
    }


}
