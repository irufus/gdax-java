package com.coinbase.exchange.websocketfeed;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class DoneOrderBookMessageTest {

    @Test
    void shouldDeserialiseDoneMessagesFromJsonSuccessfully() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = " {" +
                "   \"type\": \"done\"," +
                "   \"time\": \"2014-11-07T08:19:27.028459Z\"," +
                "   \"product_id\": \"BTC-USD\"," +
                "   \"sequence\": 10," +
                "   \"price\": \"200.2\"," +
                "   \"order_id\": \"d50ec984-77a8-460a-b958-66f114b0de9b\"," +
                "   \"reason\": \"filled\", " + // canceled
                "   \"side\": \"sell\"," +
                "   \"remaining_size\": \"0.2\"" +
                " }";

        DoneOrderBookMessage result = objectMapper.readValue(json, DoneOrderBookMessage.class);

        assertEquals("done", result.getType());
        assertEquals("2014-11-07T08:19:27.028459Z", result.getTime().toString());
        assertEquals("BTC-USD", result.getProduct_id());
        assertEquals(10L, result.getSequence());
        assertEquals(new BigDecimal(200.2).setScale(1, RoundingMode.HALF_UP), result.getPrice());
        assertEquals("d50ec984-77a8-460a-b958-66f114b0de9b", result.getOrder_id());
        assertEquals("filled", result.getReason());
        assertEquals("sell", result.getSide());
        assertEquals(new BigDecimal(0.2).setScale(1, RoundingMode.HALF_UP), result.getRemaining_size());
    }
}