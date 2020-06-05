package com.coinbase.exchange.websocketfeed;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChangedOrderBookMessageTest {

    @Test
    void shouldDeserialiseChangedMessagesFromJsonSuccessfully() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = "{" +
                "    \"type\": \"change\"," +
                "    \"time\": \"2014-11-07T08:19:27.028459Z\"," +
                "    \"sequence\": 80," +
                "    \"order_id\": \"ac928c66-ca53-498f-9c13-a110027a60e8\"," +
                "    \"product_id\": \"BTC-USD\"," +
                "    \"new_funds\": \"5.23512\"," +
                "    \"old_funds\": \"12.234412\"," +
                "    \"price\": \"400.23\"," +
                "    \"side\": \"sell\"" +
                "}";

        ChangedOrderBookMessage result = objectMapper.readValue(json, ChangedOrderBookMessage.class);

        assertEquals("change", result.getType());
        assertEquals("2014-11-07T08:19:27.028459Z", result.getTime().toString());
        assertEquals(80L, result.getSequence());
        assertEquals("ac928c66-ca53-498f-9c13-a110027a60e8", result.getOrder_id());
        assertEquals("BTC-USD", result.getProduct_id());
        assertEquals(new BigDecimal(5.23512).setScale(5, RoundingMode.HALF_UP), result.getNew_funds());
        assertEquals(new BigDecimal(12.234412).setScale(6, RoundingMode.HALF_UP), result.getOld_funds());
        assertEquals(new BigDecimal(400.23).setScale(2, RoundingMode.HALF_UP), result.getPrice());
        assertEquals("sell", result.getSide());
    }
}