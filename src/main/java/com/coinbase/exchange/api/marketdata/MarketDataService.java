package com.coinbase.exchange.api.marketdata;

import com.coinbase.exchange.api.config.GdaxStaticVariables;
import com.coinbase.exchange.api.exchange.GdaxExchange;

import java.util.List;

/**
 * Created by robevansuk on 07/02/2017.
 */
public class MarketDataService {

    GdaxExchange exchange;

    public MarketData getMarketDataOrderBook(String productId, String level) {
        String marketDataEndpoint = GdaxStaticVariables.PRODUCT_ENDPOINT + "/" + productId + "/book";
        if(level != null && !level.equals(""))
            marketDataEndpoint += "?level=" + level;
       return exchange.get(marketDataEndpoint, MarketData.class);
    }

    public List<Trade> getTrades(String productId) {
        String tradesEndpoint = GdaxStaticVariables.PRODUCT_ENDPOINT + "/" + productId + "/trades";
        return exchange.getAsList(tradesEndpoint, Trade[].class);
    }
}
