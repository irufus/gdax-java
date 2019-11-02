package com.coinbase.exchange.api.products;

import com.coinbase.exchange.api.BaseIntegrationTest;
import com.coinbase.exchange.api.entity.Product;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * See class doc for BaseIntegrationTest
 *
 * Created by robevansuk on 08/02/2017.
 */
public class ProductsIntegrationTest extends BaseIntegrationTest {

    @Autowired
    ProductService productService;

    @Test
    public void canGetProducts() {
        List<Product> products = productService.getProducts();
        products.forEach(item->System.out.println(item.getId()));
        assertTrue(products.size() >= 0);
    }
}
