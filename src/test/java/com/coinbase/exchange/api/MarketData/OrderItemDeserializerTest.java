package com.coinbase.exchange.api.MarketData;

import com.coinbase.exchange.api.marketdata.MarketData;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by ren7881 on 20/03/2017.
 */
public class OrderItemDeserializerTest {

    private Gson mapper;

    @Before
    public void setUp() {
        mapper = new Gson();
    }

    /**
     * This is now out of date - the api delivers a list of strings - amount, price, order Id. NOT number of orders as this test shows.
     *
     */
    //TODO: Fix Test MarketData is no the same as the data being tested.
    @Ignore
    @Test
    public void testDesirialization() {
        String test = "{\n" +
                "    \"sequence\": \"3\",\n" +
                "    \"bids\": [\n" +
                "        [ \"111.96\", \"2.11111\", 3 ],\n" +
                "        [ \"295.96\", \"4.39088265\", 2 ]\n" +
                "    ],\n" +
                "    \"asks\": [\n" +
                "        [ \"555.97\", \"66.5656565\", 10 ],\n" +
                "        [ \"295.97\", \"25.23542881\", 12 ]\n" +
                "    ]\n" +
                "}";

        MarketData marketData = mapper.fromJson(test, MarketData.class);
        assertTrue(marketData != null);
    }
}