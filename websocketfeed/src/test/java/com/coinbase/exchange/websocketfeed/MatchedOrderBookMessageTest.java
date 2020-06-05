package com.coinbase.exchange.websocketfeed;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class MatchedOrderBookMessageTest {

    @Test
    void shouldDeserialiseMatchedMessagesFromJsonSuccessfully() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = " {" +
                "   \"type\": \"match\"," +
                "   \"trade_id\": 10," +
                "   \"sequence\": 50," +
                "   \"maker_order_id\": \"ac928c66-ca53-498f-9c13-a110027a60e8\"," +
                "   \"taker_order_id\": \"132fb6ae-456b-4654-b4e0-d681ac05cea1\"," +
                "   \"time\": \"2014-11-07T08:19:27.028459Z\"," +
                "   \"product_id\": \"BTC-USD\"," +
                "   \"size\": \"5.23512\"," +
                "   \"price\": \"400.23\"," +
                "   \"side\": \"sell\"" +
                " }";

        MatchedOrderBookMessage result = objectMapper.readValue(json, MatchedOrderBookMessage.class);

        assertEquals("match", result.getType());
        assertEquals("10", result.getTrade_id());
        assertEquals(50L, result.getSequence());
        assertEquals("ac928c66-ca53-498f-9c13-a110027a60e8", result.getMaker_order_id());
        assertEquals("132fb6ae-456b-4654-b4e0-d681ac05cea1", result.getTaker_order_id());
        assertEquals("2014-11-07T08:19:27.028459Z", result.getTime().toString());
        assertEquals("BTC-USD", result.getProduct_id());
        assertEquals(new BigDecimal(5.23512).setScale(5, RoundingMode.HALF_UP), result.getSize());
        assertEquals(new BigDecimal(400.23).setScale(2, RoundingMode.HALF_UP), result.getPrice());
        assertEquals("sell", result.getSide());
    }

}