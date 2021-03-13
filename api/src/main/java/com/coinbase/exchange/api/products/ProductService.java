package com.coinbase.exchange.api.products;

import com.coinbase.exchange.api.exchange.CoinbaseExchange;
import com.coinbase.exchange.api.exchange.CoinbaseExchangeException;
import com.coinbase.exchange.model.Candles;
import com.coinbase.exchange.model.Granularity;
import com.coinbase.exchange.model.Product;
import org.springframework.core.ParameterizedTypeReference;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.joining;

/**
 * Created by robevansuk on 03/02/2017.
 */
public class ProductService {

    public static final String PRODUCTS_ENDPOINT = "/products";

    final CoinbaseExchange exchange;

    public ProductService(final CoinbaseExchange exchange) {
        this.exchange = exchange;
    }

    // no paged products necessary
    public List<Product> getProducts() throws CoinbaseExchangeException {
        return exchange.getAsList(PRODUCTS_ENDPOINT, new ParameterizedTypeReference<Product[]>() {
        });
    }

    public Candles getCandles(String productId) throws CoinbaseExchangeException {
        return new Candles(exchange.get(PRODUCTS_ENDPOINT + "/" + productId + "/candles", new ParameterizedTypeReference<List<String[]>>() {
        }));
    }

    public Candles getCandles(String productId, Map<String, String> queryParams) throws CoinbaseExchangeException {
        StringBuffer url = new StringBuffer(PRODUCTS_ENDPOINT + "/" + productId + "/candles");
        if (queryParams != null && queryParams.size() != 0) {
            url.append("?");
            url.append(queryParams.entrySet().stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .collect(joining("&")));
        }
        return new Candles(exchange.get(url.toString(), new ParameterizedTypeReference<List<String[]>>() {}));
    }

    /**
     * If either one of the start or end fields are not provided then both fields will be ignored.
     * If a custom time range is not declared then one ending now is selected.
     */
    public Candles getCandles(String productId, Instant startTime, Instant endTime, Granularity granularity)
            throws CoinbaseExchangeException {

        Map<String, String> queryParams = new HashMap<>();

        if (startTime != null) {
            queryParams.put("start", startTime.toString());
        }
        if (endTime != null) {
            queryParams.put("end", endTime.toString());
        }
        if (granularity != null) {
            queryParams.put("granularity", granularity.get());
        }

        return getCandles(productId, queryParams);
    }

    /**
     * The granularity field must be one of the following values: {60, 300, 900, 3600, 21600, 86400}
     */
    public Candles getCandles(String productId, Granularity granularity) throws CoinbaseExchangeException {
        return getCandles(productId, null, null, granularity);
    }

    /**
     *  If either one of the start or end fields are not provided then both fields will be ignored.
     */
    public Candles getCandles(String productId, Instant start, Instant end) throws CoinbaseExchangeException {
        return getCandles(productId, start, end, null);
    }
}
