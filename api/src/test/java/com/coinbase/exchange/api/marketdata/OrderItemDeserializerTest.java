package com.coinbase.exchange.api.marketdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by ren7881 on 20/03/2017.
 */
public class OrderItemDeserializerTest {

    private ObjectMapper mapper = new ObjectMapper();

    /**
     * This is now out of date - the api delivers a list of strings - amount, price, order Id. NOT number of orders as this test shows.
     * @throws IOException
     */
    @Test
    public void testDesirialization() throws IOException {
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

        MarketData marketData = mapper.readValue(test, MarketData.class);
        assertEquals(marketData.getAsks().size(), 2);
        assertEquals(marketData.getBids().size(), 2);
        assertEquals(marketData.getSequence(), 3L);
    }
}