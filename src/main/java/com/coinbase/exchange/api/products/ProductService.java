package com.coinbase.exchange.api.products;

import com.coinbase.exchange.api.entity.Product;
import com.coinbase.exchange.api.entity.ProductOrderBook;
import com.coinbase.exchange.api.exchange.CoinbaseExchange;
import com.coinbase.exchange.api.exchange.GenericParameterizedType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.InvalidKeyException;

/**
 * Created by robevansuk on 03/02/2017.
 */
@Component
public class ProductService {

    @Autowired
    CoinbaseExchange exchange;

    public static final String PRODUCTS_ENDPOINT = "/products";

    public Product[] getProducts() {
        return exchange.get(PRODUCTS_ENDPOINT, new GenericParameterizedType<Product[]>());
    }

    /**
     * @param product
     * @param level
     * @return ProductOrderBook
     */
    public ProductOrderBook getMarketDataProductOrderBook(String product, String level) {
        String productOrderBookEndpoint = PRODUCTS_ENDPOINT + "/" + product + "/book";
        if(level != null && !level.equals(""))
            productOrderBookEndpoint += "?level=" + level;
        return exchange.get(productOrderBookEndpoint, new GenericParameterizedType<ProductOrderBook>());
    }

}
