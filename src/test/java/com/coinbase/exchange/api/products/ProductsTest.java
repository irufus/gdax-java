package com.coinbase.exchange.api.products;

import com.coinbase.exchange.api.BaseTest;
import com.coinbase.exchange.api.entity.Product;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertTrue;

/**
 * Created by robevansuk on 08/02/2017.
 */
public class ProductsTest extends BaseTest {

    @Autowired
    ProductService productService;

    @Test
    public void canGetProducts() {
        Product[] products = productService.getProducts();
        assertTrue(products.length >= 0);
    }
}
