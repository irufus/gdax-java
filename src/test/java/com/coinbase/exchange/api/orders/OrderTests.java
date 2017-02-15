package com.coinbase.exchange.api.orders;

import com.coinbase.exchange.api.BaseTest;
import com.coinbase.exchange.api.accounts.Account;
import com.coinbase.exchange.api.accounts.AccountService;
import com.coinbase.exchange.api.entity.Fill;
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

    /**
     * Not Strictly the best test but tests placing the order and
     * then cancelling it without leaving a mess.
     */
    @Test
    public void canMakeLimitOrderAndGetTheOrderAndCancelIt() {
        String product = "BTC-USD";
        MarketData marketData = getMarketDataOrderBook(product);
        assertTrue(marketData != null);

        BigDecimal price = getAskPrice(marketData).setScale(8, BigDecimal.ROUND_HALF_UP);
        BigDecimal size = new BigDecimal(0.01).setScale(8, BigDecimal.ROUND_HALF_UP);

        NewLimitOrderSingle limitOrder = new NewLimitOrderSingle();
        limitOrder.setProduct_id("BTC-USD");
        limitOrder.setSide("buy");
        limitOrder.setType("limit");
        limitOrder.setPrice(price);
        limitOrder.setSize(size);

        Order order = orderService.createOrder(limitOrder);

        assertTrue(order!=null);
        assertEquals("BTC-USD", order.getProduct_id());
        assertEquals(size, new BigDecimal(order.getSize()).setScale(8, BigDecimal.ROUND_HALF_UP));
        assertEquals(price, new BigDecimal(order.getPrice()).setScale(8, BigDecimal.ROUND_HALF_UP));
        assertEquals("buy", order.getSide());
        assertEquals("limit", order.getType());

        orderService.cancelOrder(order.getId());
        Order[] orders = orderService.getOpenOrders();
        for (Order o : orders) {
            assertTrue(o.getId() != order.getId());
        }
    }

    @Test
    public void cancelAllOrders() {
        Order[] cancelledOrders = orderService.cancelAllOpenOrders();
        assertTrue(cancelledOrders.length >=0);
    }

    @Test
    public void getAllOpenOrders() {
        Order[] openOrders = orderService.getOpenOrders();
        assertTrue(openOrders.length >= 0);
    }

    @Test
    public void getFills() {
        Fill[] fills = orderService.getAllFills();
        assertTrue(fills.length >= 0);
    }

    private MarketData getMarketDataOrderBook(String product) {
        return marketDataService.getMarketDataOrderBook(product, "1");
    }

    private BigDecimal getAskPrice(MarketData marketData) {
        return marketData.getAsks()[0][0].setScale(4, BigDecimal.ROUND_HALF_UP);
    }
}
