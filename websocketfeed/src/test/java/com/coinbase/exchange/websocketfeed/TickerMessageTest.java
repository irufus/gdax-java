package com.coinbase.exchange.websocketfeed;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_UP;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TickerMessageTest {

    @Test
    void shouldDeserialiseSuccessfullyFromValidJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = "{" +
                "    \"type\": \"ticker\"," +
                "    \"trade_id\": 20153558," +
                "    \"sequence\": 3262786978," +
                "    \"time\": \"2017-09-02T17:05:49.250000Z\"," +
                "    \"product_id\": \"BTC-USD\"," +
                "    \"price\": \"4388.01000000\"," +
                "    \"side\": \"buy\"," +
                "    \"last_size\": \"0.03000000\"," +
                "    \"best_bid\": \"4388\"," +
                "    \"best_ask\": \"4388.01\"" +
                "}";

        TickerMessage result = objectMapper.readValue(json, TickerMessage.class);

        assertEquals("ticker", result.getType());
        assertEquals(20153558L, result.getTradeId());
        assertEquals(3262786978L, result.getSequence());
        assertEquals("2017-09-02T17:05:49.250Z", result.getTime().toString());
        assertEquals("BTC-USD", result.getProductId());
        assertEquals(new BigDecimal("4388.01000000").setScale(8, HALF_UP), result.getPrice());
        assertEquals("buy", result.getSide());
        assertEquals(new BigDecimal(4388).setScale(0, HALF_UP), result.getBestBid());
        assertEquals(new BigDecimal("4388.01").setScale(2, HALF_UP), result.getBestAsk());


    }
}