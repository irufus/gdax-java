package com.coinbase.exchange.api.orders;

import com.coinbase.exchange.api.BaseIntegrationTest;
import com.coinbase.exchange.api.accounts.Account;
import com.coinbase.exchange.api.accounts.AccountService;
import com.coinbase.exchange.api.marketdata.MarketData;
import com.coinbase.exchange.api.marketdata.MarketDataService;
import com.coinbase.exchange.api.products.ProductService;
import com.coinbase.exchange.model.Fill;
import com.coinbase.exchange.model.NewLimitOrderSingle;
import com.coinbase.exchange.model.NewMarketOrderSingle;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * See class doc for BaseIntegrationTest
 *
 * Created by Ishmael (sakamura@gmail.com) on 6/18/2016.
 */
public class OrderIntegrationTest extends BaseIntegrationTest {

    private static final Logger log = LoggerFactory.getLogger(OrderIntegrationTest.class);

    ProductService productService; // TODO Mock using mockito + mocked responses (hint: use @Mock annotation)
    AccountService accountService; // TODO Mock
    MarketDataService marketDataService;// TODO Mock

    OrderService testee;

    // accounts: BTC, USD, GBP, EUR, CAD
    // products: BTC-USD, BTC-GBP, BTC-EUR, ETH-BTC, ETH-USD, LTC-BTC, LTC-USD

    /**
     * Test is too complex. This test tests placing an order and
     * then cancelling it without leaving a mess.
     * Note: You'll need credit available in your test account
     */
    @Ignore
    public void canMakeLimitOrderAndGetTheOrderAndCancelIt() {
        List<Account> accounts = accountService.getAccounts();
        Optional<Account> accountsWithMoreThanZeroCoinsAvailable = accounts.stream()
                .filter(account -> account.getBalance().compareTo(BigDecimal.ONE) > 0 && account.getCurrency().contains("BTC"))
                .findFirst();

        assertTrue(accountsWithMoreThanZeroCoinsAvailable.isPresent());

        String productId;
        if (accountsWithMoreThanZeroCoinsAvailable.get().getCurrency().equals("BTC")) {
            productId = accountsWithMoreThanZeroCoinsAvailable.get().getCurrency() + "-USD";
        } else {
            productId = accountsWithMoreThanZeroCoinsAvailable.get().getCurrency() + "-BTC";
        }

        MarketData marketData = getMarketDataOrderBook(productId);

        assertNotNull(marketData);

        BigDecimal price = getAskPrice(marketData).setScale(8, RoundingMode.HALF_UP);
        BigDecimal size = new BigDecimal("0.01").setScale(8, RoundingMode.HALF_UP);

        NewLimitOrderSingle limitOrder = getNewLimitOrderSingle(productId, price, size);

        Order order = testee.createOrder(limitOrder);

        assertNotNull(order);
        assertEquals(productId, order.getProduct_id());
        assertEquals(size, new BigDecimal(order.getSize()).setScale(8, RoundingMode.HALF_UP));
        assertEquals(price, new BigDecimal(order.getPrice()).setScale(8, RoundingMode.HALF_UP));
        assertEquals("limit", order.getType());

        testee.cancelOrder(order.getId());
        List<Order> orders = testee.getOpenOrders();
        orders.stream().forEach(o -> assertTrue(o.getId() != order.getId()));
    }

    @Test
    public void cancelAllOrders() {
        List<Order> cancelledOrders = testee.cancelAllOpenOrders();
        assertTrue(cancelledOrders.size() >= 0);
    }

    @Test
    public void getAllOpenOrders() {
        List<Order> openOrders = testee.getOpenOrders();
        assertTrue(openOrders.size() >= 0);
    }

    @Test
    public void getFillsByProductId() {
        List<Fill> fills = testee.getFillsByProductId("BTC-USD", 100);
        assertTrue(fills.size() >= 0);
    }

    @Ignore
    public void shouldGetFilledByOrderIdWhenMakingMarketOrderBuy() {
        NewMarketOrderSingle marketOrder = createNewMarketOrder("BTC-USD", "buy", new BigDecimal(0.01));
        Order order = testee.createOrder(marketOrder);

        List<Fill> fills = testee.getFillByOrderId(order.getId(), 100);

        assertEquals(1, fills.size());
    }

    @Ignore
    public void createMarketOrderBuy() {
        NewMarketOrderSingle marketOrder = createNewMarketOrder("BTC-USD", "buy", new BigDecimal(0.01));
        Order order = testee.createOrder(marketOrder);

        assertNotNull(order); //make sure we created an order
        String orderId = order.getId();
        assertTrue(orderId.length() > 0); //ensure we have an actual orderId
        Order filledOrder = testee.getOrder(orderId);
        assertNotNull(filledOrder); //ensure our order hit the system
        assertTrue(new BigDecimal(filledOrder.getSize()).compareTo(BigDecimal.ZERO) > 0); //ensure we got a fill
        log.info("Order opened and filled: " + filledOrder.getSize() + " @ " + filledOrder.getExecuted_value()
                + " at the cost of " + filledOrder.getFill_fees());
    }

    @Ignore
    public void createMarketOrderSell() {
        NewMarketOrderSingle marketOrder = createNewMarketOrder("BTC-USD", "sell", new BigDecimal(0.01));
        Order order = testee.createOrder(marketOrder);
        assertNotNull(order); //make sure we created an order
        String orderId = order.getId();
        assertTrue(orderId.length() > 0); //ensure we have an actual orderId
        Order filledOrder = testee.getOrder(orderId);
        assertNotNull(filledOrder); //ensure our order hit the system
        assertTrue(new BigDecimal(filledOrder.getSize()).compareTo(BigDecimal.ZERO) > 0); //ensure we got a fill
        log.info("Order opened and filled: " + filledOrder.getSize() + " @ " + filledOrder.getExecuted_value()
                + " at the cost of " + filledOrder.getFill_fees());
    }

    private NewMarketOrderSingle createNewMarketOrder(String product, String action, BigDecimal size) {
        NewMarketOrderSingle marketOrder = new NewMarketOrderSingle();
        marketOrder.setProduct_id(product);
        marketOrder.setSide(action);
        marketOrder.setSize(size);
        return marketOrder;
    }

    private MarketData getMarketDataOrderBook(String product) {
        return marketDataService.getMarketDataOrderBook(product, 1);
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
        return marketData.getAsks().get(0).getPrice().setScale(4, RoundingMode.HALF_UP);
    }

    /**
     * @param accountsAvailable Available accounts to trade from
     * @return null or String
     */
    private MarketData getTradeableProductData(List<Account> accountsAvailable) {
        MarketData data = null;
        for (Account account : accountsAvailable) {
            System.out.println("Do nothing");
        }
        return data;
    }
}
