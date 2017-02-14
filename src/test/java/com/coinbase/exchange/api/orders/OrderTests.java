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
import org.omg.CORBA.UserException;
import org.omg.CORBA.portable.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.MissingRequiredPropertiesException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.function.Supplier;

import static com.sun.tools.internal.ws.wsdl.parser.Util.fail;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
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

    // accounts: BTC, USD, GBP, EUR, CAD
    // products: BTC-USD, BTC-GBP, BTC-EUR, ETH-BTC, ETH-USD, LTC-BTC, LTC-USD
    @Test
    public void canMakeLimitOrder(){
        try {
            Product[] products = productService.getProducts();
            Account[] accounts = accountService.getAccounts();

            Account usdAccountTotalHeld = Arrays.stream(accounts)
                    .filter(it -> it.getCurrency().equals("USD"))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No USD Accounts found"));
            OrderBuilder builder = new OrderBuilder();

            MarketData marketData = null;
            for(Product product : products){
                Order order = builder.setProduct_id(product.getId())
                        .setSize(new BigDecimal(1.0))
                        .setSide("BUY")
                        .setProduct_id(product.getId())
                        .build();
                marketData = marketDataService.getMarketDataOrderBook(product.getId(), "1");
                assertTrue(marketData != null);

                NewLimitOrderSingle limitOrder = new NewLimitOrderSingle();
                limitOrder.setSide("buy");

                BigDecimal dollarPrice = marketData.getAsks()[0][0].setScale(2, BigDecimal.ROUND_HALF_UP);
                BigDecimal bitcoinAmount = marketData.getAsks()[0][1].setScale(8, BigDecimal.ROUND_HALF_UP);
                BigDecimal numberOfOrders = marketData.getAsks()[0][2].setScale(1, BigDecimal.ROUND_HALF_UP);

                limitOrder.setPrice(marketData.getAsks()[0][0].setScale(8, BigDecimal.ROUND_HALF_UP));
                limitOrder.setSize(marketData.getAsks()[0][1].setScale(2, BigDecimal.ROUND_HALF_UP));
                limitOrder.setProduct_id(product.getId());

                orderService.createOrder(limitOrder);
                Order[] openOrders = orderService.getOpenOrders();
                assertEquals(1, openOrders.length);

            }
            // assert something rather than nothing here.

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
