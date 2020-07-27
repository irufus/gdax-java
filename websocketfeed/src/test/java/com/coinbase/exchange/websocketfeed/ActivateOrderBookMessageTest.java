package com.coinbase.exchange.websocketfeed;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class ActivateOrderBookMessageTest {

    @Test
    void shouldDeserialiseActivateMessagesFromJsonSuccessfully() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = "{" +
                "           \"type\": \"activate\"," +
                "           \"product_id\": \"BTC-GBP\"," +
                "           \"timestamp\": \"1483736448.299000\"," +
                "           \"user_id\": \"12\"," +
                "           \"profile_id\": \"30000727-d308-cf50-7b1c-c06deb1934fc\"," +
                "           \"order_id\": \"7b52009b-64fd-0a2a-49e6-d8a939753077\"," +
                "           \"stop_type\": \"entry\"," +
                "           \"side\": \"buy\"," +
                "           \"stop_price\": \"80\"," +
                "           \"size\": \"2\"," +
                "           \"funds\": \"50\"," +
                "           \"private\": true" +
                "}";

        ActivateOrderBookMessage result = objectMapper.readValue(json, ActivateOrderBookMessage.class);

        assertEquals("activate", result.getType());
        assertEquals("BTC-GBP", result.getProduct_id());
        assertEquals(1483736448, result.getTimestamp().getEpochSecond());
        assertEquals(299000000, result.getTimestamp().getNano());
        assertEquals("12", result.getUser_id());
        assertEquals("30000727-d308-cf50-7b1c-c06deb1934fc", result.getProfile_id());
        assertEquals("7b52009b-64fd-0a2a-49e6-d8a939753077", result.getOrder_id());
        assertEquals("entry", result.getStop_type());
        assertEquals("buy", result.getSide());
        assertEquals(new BigDecimal(80), result.getStop_price());
        assertEquals(new BigDecimal(2), result.getSize());
        assertEquals(new BigDecimal(50), result.getFunds());
        assertTrue(result.isPrivateFlag());
    }

}