package com.coinbase.exchange.api.MarketData;

import com.coinbase.exchange.api.BaseTest;
import com.coinbase.exchange.api.marketdata.MarketData;
import com.coinbase.exchange.api.marketdata.MarketDataService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertTrue;

/**
 * Created by robevansuk on 14/02/2017.
 */
public class MarketDataTest extends BaseTest {

    @Autowired
    MarketDataService marketDataService;

    @Test
    public void canGetMarketData() {
        MarketData marketData = marketDataService.getMarketDataOrderBook("BTC-USD", "1");
        assertTrue(marketData.getSequence() > 0);
        assertTrue(marketData.getAsks()[0].length == 3);
        assertTrue(marketData.getBids()[0].length == 3);
    }
}
