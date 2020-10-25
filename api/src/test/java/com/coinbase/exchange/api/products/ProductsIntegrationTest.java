package com.coinbase.exchange.api.products;

import com.coinbase.exchange.api.BaseIntegrationTest;
import com.coinbase.exchange.api.config.IntegrationTestConfiguration;
import com.coinbase.exchange.model.Candles;
import com.coinbase.exchange.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * See class doc for BaseIntegrationTest
 *
 * Created by robevansuk on 08/02/2017.
 */
@ExtendWith(SpringExtension.class)
@Import({IntegrationTestConfiguration.class})
public class ProductsIntegrationTest extends BaseIntegrationTest {

    private static final String TEST_PRODUCT_ID = "BTC-GBP";

    private ProductService productService;

    @BeforeEach
    void setUp() {
        this.productService = new ProductService(exchange);
    }

    @Test
    public void canGetProducts() {
        List<Product> products = productService.getProducts();
        products.forEach(item->System.out.println(item.getId()));
        assertTrue(products.size() >= 0);
    }

    @Test
    void shouldGetCandles() {
        Candles candles = productService.getCandles(TEST_PRODUCT_ID);

        assertTrue(candles.getCandleList().size() > 5);
    }
}
