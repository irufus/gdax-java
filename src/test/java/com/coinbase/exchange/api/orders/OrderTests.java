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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
     * Note: You'll need credit available
     */
    @Test
    public void canMakeLimitOrderAndGetTheOrderAndCancelIt() {
        List<Account> accounts = accountService.getAccounts();
        Optional<Account> accountsWithMoreThanZeroCoinsAvailable = accounts.stream()
                .filter(account -> account.getBalance().compareTo(BigDecimal.ONE) > 0)
                .findFirst();

        assertTrue(accountsWithMoreThanZeroCoinsAvailable.isPresent());

        String productId;
        if (accountsWithMoreThanZeroCoinsAvailable.get().equals("BTC")) {
            productId = accountsWithMoreThanZeroCoinsAvailable.get().getCurrency() + "-USD";
        } else {
            productId = accountsWithMoreThanZeroCoinsAvailable.get().getCurrency() + "-BTC";
        }

        MarketData marketData = getMarketDataOrderBook(productId);

        assertTrue(marketData != null);

        BigDecimal price = getAskPrice(marketData).setScale(8, BigDecimal.ROUND_HALF_UP);
        BigDecimal size = new BigDecimal("0.01").setScale(8, BigDecimal.ROUND_HALF_UP);

        NewLimitOrderSingle limitOrder = getNewLimitOrderSingle(productId, price, size);

        Order order = orderService.createOrder(limitOrder);

        assertTrue(order != null);
        assertEquals(productId, order.getProduct_id());
        assertEquals(size, new BigDecimal(order.getSize()).setScale(8, BigDecimal.ROUND_HALF_UP));
        assertEquals(price, new BigDecimal(order.getPrice()).setScale(8, BigDecimal.ROUND_HALF_UP));
        assertEquals("limit", order.getType());

        orderService.cancelOrder(order.getId());
        List<Order> orders = orderService.getOpenOrders();
        orders.stream().forEach(o -> assertTrue(o.getId() != order.getId()));
    }

    @Test
    public void cancelAllOrders() {
        List<Order> cancelledOrders = orderService.cancelAllOpenOrders();
        assertTrue(cancelledOrders.size() >=0);
    }

    @Test
    public void getAllOpenOrders() {
        List<Order> openOrders = orderService.getOpenOrders();
        assertTrue(openOrders.size() >= 0);
    }

    @Test
    public void getFills() {
        List<Fill> fills = orderService.getAllFills();
        assertTrue(fills.size() >= 0);
    }

    private MarketData getMarketDataOrderBook(String product) {
        return marketDataService.getMarketDataOrderBook(product, "1");
    }

    private NewLimitOrderSingle getNewLimitOrderSingle(String productId, BigDecimal price, BigDecimal size) {
        NewLimitOrderSingle limitOrder = new NewLimitOrderSingle();
        limitOrder.setProduct_id(productId);
        if (productId.contains("-BTC")) {
            limitOrder.setSide("sell");
        } else {
            limitOrder.setSide("buy");
        }
        limitOrder.setType("limit");
        limitOrder.setPrice(price);
        limitOrder.setSize(size);
        return limitOrder;
    }

    private BigDecimal getAskPrice(MarketData marketData) {
        return marketData.getAsks().get(0).getPrice().setScale(4, BigDecimal.ROUND_HALF_UP);
    }
}
