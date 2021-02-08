package com.coinbase.exchange.api.products;

import com.coinbase.exchange.api.BaseIntegrationTest;
import com.coinbase.exchange.api.config.IntegrationTestConfiguration;
import com.coinbase.exchange.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * See class doc for BaseIntegrationTest
 * <p>
 * Created by robevansuk on 08/02/2017.
 */
@ExtendWith(SpringExtension.class)
@Import({IntegrationTestConfiguration.class})
public class ProductsIntegrationTest extends BaseIntegrationTest {

    private static final String TEST_PRODUCT_ID = "BTC-GBP";
    public static final int ONE_DAY_IN_SECONDS = 60 * 60 * 24;
    public static final int SIX_HOURS_IN_SECONDS = 60 * 60 * 6;
    public static final int ONE_HOUR_IN_SECONDS = 60 * 60;
    public static final int FIFTEEN_MINS_IN_SECONDS = 60 * 15;
    public static final int FIVE_MINS_IN_SECONDS = 60 * 5;
    public static final int ONE_MIN_IN_SECONDS = 60;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        this.productService = new ProductService(exchange);
    }

    @Test
    public void canGetProducts() {
        List<Product> products = productService.getProducts();
        products.forEach(item -> System.out.println(item.getId()));
        assertTrue(products.size() >= 0);
    }

    @Test
    void shouldGetCandles() {
        Candles candles = productService.getCandles(TEST_PRODUCT_ID);

        assertEquals(300, candles.getCandleList().size());
    }

    @Test
    void shouldGetCandlesForAGanularityOf_OneDay() {
        Candles candles = productService.getCandles(TEST_PRODUCT_ID, Granularity.ONE_DAY);

        assertEquals(300, candles.getCandleList().size());
        assertEquals(ONE_DAY_IN_SECONDS, getDuration(candles).getSeconds());
    }

    @Test
    void shouldGetCandlesForAGanularityOf_SixHours() {
        Candles candles = productService.getCandles(TEST_PRODUCT_ID, Granularity.SIX_HOURS);

        assertEquals(300, candles.getCandleList().size());
        assertEquals(SIX_HOURS_IN_SECONDS, getDuration(candles).getSeconds());
    }

    @Test
    void shouldGetCandlesForAGanularityOf_OneHour() {
        Candles candles = productService.getCandles(TEST_PRODUCT_ID, Granularity.ONE_HOUR);

        assertEquals(300, candles.getCandleList().size());
        assertEquals(ONE_HOUR_IN_SECONDS, getDuration(candles).getSeconds());
    }

    @Test
    void shouldGetCandlesForAGanularityOf_FifteenMins() {
        Candles candles = productService.getCandles(TEST_PRODUCT_ID, Granularity.FIFTEEN_MIN);

        assertEquals(300, candles.getCandleList().size());
        assertEquals(FIFTEEN_MINS_IN_SECONDS, getDuration(candles).getSeconds());
    }

    @Test
    void shouldGetCandlesForAGanularityOf_FiveMins() {
        Candles candles = productService.getCandles(TEST_PRODUCT_ID, Granularity.FIVE_MIN);

        assertEquals(300, candles.getCandleList().size());
        assertEquals(FIVE_MINS_IN_SECONDS, getDuration(candles).getSeconds());
    }

    @Test
    void shouldGetCandlesForAGanularityOf_OneMin() {
        Candles candles = productService.getCandles(TEST_PRODUCT_ID, Granularity.ONE_MIN);

        assertEquals(300, candles.getCandleList().size());
        assertEquals(ONE_MIN_IN_SECONDS, getDuration(candles).getSeconds());
    }

    @Test
    void shouldGetCandlesForWithAStartAndEndTime() {
        Instant startTime = Instant.now().minus(400, ChronoUnit.DAYS);
        Instant endTime = Instant.now().minus(100, ChronoUnit.DAYS);

        Candles candles = productService.getCandles(TEST_PRODUCT_ID, startTime, endTime, Granularity.ONE_DAY);

        // can't predict how many candles we'll get back but going back more than 300 days
        // doesn't return 300 candles here.. easiest just to assert we get more than 100 back.
        assertTrue(candles.getCandleList().size() > 100);
    }

    @Test
    void canGetStats() {
        ProductStats productStats = productService.getProductStats(TEST_PRODUCT_ID);
        Assertions.assertNotNull(productStats);
    }

    @Test
    void canGetAllStats() {
        List<Stats> stats = productService.getStats();
        Assertions.assertNotNull(stats);
    }

    private Duration getDuration(Candles candles) {
        Instant candleTime1 = candles.getCandleList().get(0).getTime();
        Instant candleTime2 = candles.getCandleList().get(1).getTime();
        return Duration.between(candleTime2, candleTime1);
    }
}
