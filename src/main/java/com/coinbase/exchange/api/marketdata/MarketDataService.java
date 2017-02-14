package com.coinbase.exchange.api.marketdata;

import com.coinbase.exchange.api.exchange.CoinbaseExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.InvalidKeyException;

/**
 * Created by robevansuk on 07/02/2017.
 */
@Component
public class MarketDataService {

    @Autowired
    CoinbaseExchange exchange;


    public static final String PRODUCT_ENDPOINT = "/products";

    public MarketData getMarketDataOrderBook(String productId, String level) throws IOException, CloneNotSupportedException, InvalidKeyException {
        String marketDataEndpoint = PRODUCT_ENDPOINT + "/" + productId + "/book";
        if(level != null && !level.equals(""))
            marketDataEndpoint += "?level=" + level;

       return exchange.get(marketDataEndpoint, new ParameterizedTypeReference<MarketData>(){});

    }
}
