package com.coinbase.exchange.websocketfeed;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class L2UpdateMessageTest {

    @Test
    void shouldDeserialiseL2UpdateMessagesSuccessfully() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = "{ \"type\": \"l2update\"," +
                "  \"product_id\": \"BTC-GBP\"," +
                "  \"changes\": [" +
                "    [" +
                "      \"buy\"," +
                "      \"5454.12\"," +
                "      \"0.00000000\"" +
                "    ]" +
                "  ]," +
                "  \"time\": \"2020-04-10T15:28:07.393966Z\"" +
                "}";

        L2UpdateMessage result = objectMapper.readValue(json, L2UpdateMessage.class);

        assertEquals("l2update", result.getType());
        assertEquals("BTC-GBP", result.getProductId());
        assertEquals("buy", result.getChanges()[0][0]);
        assertEquals("5454.12", result.getChanges()[0][1]);
        assertEquals("0.00000000", result.getChanges()[0][2]);
        assertEquals("2020-04-10T15:28:07.393966Z", result.getTime().toString());
    }
}