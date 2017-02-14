package com.coinbase.exchange.api.orders;

import com.coinbase.exchange.api.BaseTest;
import com.coinbase.exchange.api.accounts.Account;
import com.coinbase.exchange.api.accounts.AccountService;
import com.coinbase.exchange.api.entity.NewLimitOrderSingle;
import com.coinbase.exchange.api.entity.Product;
import com.coinbase.exchange.api.marketdata.MarketData;
import com.coinbase.exchange.api.marketdata.MarketDataService;
import com.coinbase.exchange.api.products.ProductService;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Ishmael (sakamura@gmail.com) on 6/18/2016.
 */
public class OrderTests extends BaseTest {

    static final Logger log = Logger.getLogger(OrderTests.class);

    @Autowired
    ProductService productService;

    @Autowired
    AccountService accountService;

    @Autowired
    MarketDataService marketDataService;

    @Autowired
    OrderService orderService;

    // accounts: BTC, USD, GBP, EUR, CAD
    // products: BTC-USD, BTC-GBP, BTC-EUR, ETH-BTC, ETH-USD, LTC-BTC, LTC-USD
    @Test
    public void canMakeLimitOrder() {
        try {
            Product product = getUsdProduct();
            MarketData marketData = getMarketDataOrderBook(product);
            assertTrue(marketData != null);

            BigDecimal price = getAskPrice(marketData).setScale(8, BigDecimal.ROUND_HALF_UP);
            BigDecimal size = new BigDecimal(0.01).setScale(8, BigDecimal.ROUND_HALF_UP);

            NewLimitOrderSingle limitOrder = new NewLimitOrderSingle();
            limitOrder.setSide("buy");
            limitOrder.setPrice(price);
            limitOrder.setSize(size);
            limitOrder.setProduct_id(product.getId());
            limitOrder.setType("limit");

            Order order = orderService.createOrder(limitOrder);
            assertTrue(order!=null);
            assertEquals(size, new BigDecimal(order.getSize()).setScale(8, BigDecimal.ROUND_HALF_UP));
            assertEquals(price, new BigDecimal(order.getPrice()).setScale(8, BigDecimal.ROUND_HALF_UP));
            assertEquals("buy", order.getSide());
            assertEquals("limit", order.getType());
            assertEquals("BTC-USD", order.getProduct_id());
        } catch (HttpClientErrorException ex) {
            log.error(ex.getResponseBodyAsString());
            Assert.fail();
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

    private BigDecimal getAskPrice(MarketData marketData) {
        return marketData.getAsks()[0][0].setScale(4, BigDecimal.ROUND_HALF_UP);
    }

    private Product getUsdProduct() {
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
