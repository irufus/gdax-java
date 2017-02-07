package com.coinbase.exchange.api.marketdata;

import com.coinbase.exchange.api.exchange.CoinbaseExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.Optional;

/**
 * Created by ren7881 on 07/02/2017.
 */
@Component
public class MarketDataService {

    @Autowired
    CoinbaseExchange exchange;

    @Autowired
    RestTemplate restTemplate;

    public static final String PRODUCT_ENDPOINT = "/products";

    public String getMarketDataOrderBook(String productId, Optional<String> level) throws IOException, CloneNotSupportedException, InvalidKeyException {
        String marketDataEndpoint = PRODUCT_ENDPOINT + "/" + productId + "/book";
        if(level.isPresent())
            marketDataEndpoint += "?level=" + level;
        ResponseEntity<String> marketDataOrderBook = restTemplate.exchange(marketDataEndpoint,
                HttpMethod.GET,
                exchange.securityHeaders(marketDataEndpoint, "GET", ""),
                String.class);
        return marketDataOrderBook.getBody();
    }
}
