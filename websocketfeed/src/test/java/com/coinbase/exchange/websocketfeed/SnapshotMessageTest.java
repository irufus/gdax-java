package com.coinbase.exchange.websocketfeed;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SnapshotMessageTest {

    @Test
    void shouldDeserialiseJsonSuccessfullyForValidJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "{" +
                "    \"type\": \"snapshot\"," +
                "    \"product_id\": \"BTC-USD\"," +
                "    \"bids\": [[\"10101.10\", \"0.45054140\"]]," +
                "    \"asks\": [[\"10102.55\", \"0.57753524\"]]" +
                "}";

        SnapshotMessage result = objectMapper.readValue(json, SnapshotMessage.class);

        assertEquals("snapshot", result.getType());
        assertEquals("BTC-USD", result.getProductId());
        assertArrayEquals(new String[][] { new String[]{"10101.10", "0.45054140"}}, result.getBids());
        assertArrayEquals(new String[][] { new String[]{"10102.55", "0.57753524"}}, result.getAsks());
    }
}