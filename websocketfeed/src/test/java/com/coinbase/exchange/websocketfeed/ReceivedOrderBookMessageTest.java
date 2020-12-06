package com.coinbase.exchange.websocketfeed;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReceivedOrderBookMessageTest {

    @Test
    void shouldDeserialiseReceivedMessagesFromJsonSuccessfully() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = "{" +
                "    \"type\": \"received\"," +
                "    \"time\": \"2014-11-07T08:19:27.028459Z\"," +
                "    \"product_id\": \"BTC-USD\"," +
                "    \"sequence\": 10," +
                "    \"order_id\": \"d50ec984-77a8-460a-b958-66f114b0de9b\"," +
                "    \"size\": \"1.34\"," +
                "    \"price\": \"502.1\"," +
                "    \"side\": \"buy\"," +
                "    \"order_type\": \"limit\"" +
                "  }";

        ReceivedOrderBookMessage result = objectMapper.readValue(json, ReceivedOrderBookMessage.class);

        assertEquals("received", result.getType());
        assertEquals("2014-11-07T08:19:27.028459Z", result.getTime().toString());
        assertEquals("BTC-USD", result.getProductId());
        assertEquals(10L, result.getSequence());
        assertEquals("d50ec984-77a8-460a-b958-66f114b0de9b", result.getOrderId());
        assertEquals(new BigDecimal("1.34").setScale(2, RoundingMode.HALF_UP), result.getSize());
        assertEquals(new BigDecimal("502.1").setScale(1, RoundingMode.HALF_UP), result.getPrice());
        assertEquals("buy", result.getSide());
        assertEquals("limit", result.getOrderType());
    }

}