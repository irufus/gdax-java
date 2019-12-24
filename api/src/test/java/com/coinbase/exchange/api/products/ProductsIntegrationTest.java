package com.coinbase.exchange.api.products;

import com.coinbase.exchange.api.BaseIntegrationTest;
import com.coinbase.exchange.api.entity.Product;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * See class doc for BaseIntegrationTest
 *
 * Created by robevansuk on 08/02/2017.
 */
public class ProductsIntegrationTest extends BaseIntegrationTest {

    ProductService productService;

    @Test
    public void canGetProducts() {
        List<Product> products = productService.getProducts();
        products.forEach(item->System.out.println(item.getId()));
        assertTrue(products.size() >= 0);
    }
}
