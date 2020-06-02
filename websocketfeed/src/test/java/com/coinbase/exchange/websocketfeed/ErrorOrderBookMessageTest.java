package com.coinbase.exchange.websocketfeed;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorOrderBookMessageTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldDeserialiseAllFieldsForErrorOrderBookMessage() throws JsonProcessingException {
        String carJson = "{ \"type\": \"error\", \"message\": \"error message\" }";

        ErrorOrderBookMessage errorMessage = objectMapper.readValue(carJson, ErrorOrderBookMessage.class);

        assertEquals("error", errorMessage.getType());
        assertEquals("error message", errorMessage.getMessage());

    }



}