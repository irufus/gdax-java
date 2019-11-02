package com.coinbase.exchange.api.marketdata;

import com.coinbase.exchange.api.BaseIntegrationTest;
import com.coinbase.exchange.api.entity.Product;
import com.coinbase.exchange.api.products.ProductService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * See class doc for BaseIntegrationTest
 *
 * Created by robevansuk on 14/02/2017.
 */
public class MarketDataIntegrationTest extends BaseIntegrationTest {

    @Autowired
    MarketDataService marketDataService;
    @Autowired
    ProductService productService;

    @Test
    public void canGetMarketDataForLevelOneBidAndAsk() {
        MarketData marketData = marketDataService.getMarketDataOrderBook("BTC-GBP", "1");
        System.out.println(marketData);
        assertTrue(marketData.getSequence() > 0);
    }

    @Test
    public void canGetMarketDataForLevelTwoBidAndAsk() {
        MarketData marketData = marketDataService.getMarketDataOrderBook("BTC-GBP", "2");
        System.out.println(marketData);
        assertTrue(marketData.getSequence() > 0);
    }

    /**
     * note that the returned results are slightly different for level 3. For level 3 you will see an
     * order Id rather than the count of orders at a certain price.
     */
    @Test
    public void canGetMarketDataForLevelThreeBidAndAsk() {
        MarketData marketData = marketDataService.getMarketDataOrderBook("BTC-GBP", "3");
        System.out.println(marketData);
        assertTrue(marketData.getSequence() > 0);
    }

    @Test
    public void canGetLevel1DataForAllProducts(){
        List<Product> products = productService.getProducts();
        for(Product product : products){
            System.out.print("\nTesting: " + product.getId());
            MarketData data = marketDataService.getMarketDataOrderBook(product.getId(), "1");
            assertNotNull(data);

            if(data.getBids().size() > 0 && data.getAsks().size() > 0)
                System.out.print(" B: " + data.getBids().get(0).getPrice() + " A: " + data.getAsks().get(0).getPrice());
            else
                System.out.print(" NO DATA ");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
