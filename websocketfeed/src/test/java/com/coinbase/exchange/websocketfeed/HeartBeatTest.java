package com.coinbase.exchange.websocketfeed;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HeartBeatTest {

    @Test
    void shouldDeserialiseHeartbeatMessageSuccessfully() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = "{" +
                "    \"type\": \"heartbeat\",                 " +
                "    \"sequence\": 90,                      " +
                "    \"last_trade_id\": 20," +
                "    \"product_id\": \"BTC-USD\",             " +
                "    \"time\": \"2014-11-07T08:19:28.464459Z\"" +
                "}";

        HeartBeat result = objectMapper.readValue(json, HeartBeat.class);

        assertEquals("heartbeat", result.getType());
        assertEquals(90L, result.getSequence());
        assertEquals(20, result.getLast_trade_id());
        assertEquals("BTC-USD", result.getProduct_id());
        assertEquals("2014-11-07T08:19:28.464459Z", result.getTime().toString());

    }
}