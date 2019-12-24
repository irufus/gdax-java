package com.coinbase.exchange.api.products;

import com.coinbase.exchange.api.entity.Product;
import com.coinbase.exchange.api.exchange.CoinbaseExchange;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

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
    public List<Product> getProducts() {
        return exchange.getAsList(PRODUCTS_ENDPOINT, new ParameterizedTypeReference<Product[]>(){});
    }
}
