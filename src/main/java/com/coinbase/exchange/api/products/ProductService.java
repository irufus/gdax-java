package com.coinbase.exchange.api.products;

import com.coinbase.exchange.api.config.GdaxStaticVariables;
import com.coinbase.exchange.api.entity.Product;
import com.coinbase.exchange.api.exchange.GdaxExchange;

import java.util.List;

/**
 * Created by robevansuk on 03/02/2017.
 */
public class ProductService {

    GdaxExchange exchange;

    // no paged products necessary
    public List<Product> getProducts() {
        return exchange.getAsList(GdaxStaticVariables.PRODUCTS_ENDPOINT, Product[].class);
    }
}
