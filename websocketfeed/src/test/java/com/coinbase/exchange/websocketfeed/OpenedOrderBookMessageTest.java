package com.coinbase.exchange.websocketfeed;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OpenedOrderBookMessageTest {

    @Test
    void shouldDeserialiseOpenedMessagesFromJsonSuccessfully() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = "{" +
                "  \"type\": \"open\"," +
                "  \"time\": \"2014-11-07T08:19:27.028459Z\"," +
                "  \"product_id\": \"BTC-USD\"," +
                "  \"sequence\": 10," +
                "  \"order_id\": \"d50ec984-77a8-460a-b958-66f114b0de9b\"," +
                "  \"price\": \"200.2\"," +
                "  \"remaining_size\": \"1.00\"," +
                "  \"side\": \"sell\"" +
                "}";

        OpenedOrderBookMessage result = objectMapper.readValue(json, OpenedOrderBookMessage.class);

        assertEquals("open", result.getType());
        assertEquals("2014-11-07T08:19:27.028459Z", result.getTime().toString());
        assertEquals("BTC-USD", result.getProductId());
        assertEquals(10L, result.getSequence());
        assertEquals("d50ec984-77a8-460a-b958-66f114b0de9b", result.getOrderId());
        assertEquals(new BigDecimal("1.00").setScale(2, RoundingMode.HALF_UP), result.getRemainingSize());
        assertEquals(new BigDecimal("200.2").setScale(1, RoundingMode.HALF_UP), result.getPrice());
        assertEquals("sell", result.getSide());
    }

}