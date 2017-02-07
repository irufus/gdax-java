package com.coinbase.exchange.api.products;

import com.coinbase.exchange.api.entity.Product;
import com.coinbase.exchange.api.entity.ProductOrderBook;
import com.coinbase.exchange.api.exchange.CoinbaseExchange;
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

    @Autowired
    RestTemplate restTemplate;

    public static final String PRODUCTS_ENDPOINT = "/products";

    public Product[] getProducts() throws IOException, CloneNotSupportedException, InvalidKeyException {
        ResponseEntity<Product[]> response = restTemplate.exchange(PRODUCTS_ENDPOINT,
                HttpMethod.GET,
                exchange.securityHeaders(PRODUCTS_ENDPOINT, "GET", ""),
                Product[].class);
        return response.getBody();
    }

    /**
     *
     * @param product
     * @param level
     * @return ProductOrderBook
     * @throws IOException
     */
    public ProductOrderBook getMarketDataProductOrderBook(String product, String level) throws IOException, CloneNotSupportedException, InvalidKeyException {
        String productOrderBookendpoint = "/products/" + product + "/book";
        if(level != null && !level.equals(""))
            productOrderBookendpoint += "?level=" + level;

        ResponseEntity<ProductOrderBook> response = restTemplate.exchange(productOrderBookendpoint,
                HttpMethod.GET,
                exchange.securityHeaders(productOrderBookendpoint, "GET", ""),
                ProductOrderBook.class);
        return response.getBody();
    }

}
