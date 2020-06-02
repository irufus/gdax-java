package com.coinbase.exchange.websocketfeed;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;

import static java.math.RoundingMode.HALF_UP;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StatusMessageTest {

    @Test
    void shouldDeserialiseSuccessfullyFromValidJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = "{" +
                "    \"type\": \"status\"," +
                "    \"products\": [" +
                "        {" +
                "            \"id\": \"BTC-USD\"," +
                "            \"base_currency\": \"BTC\"," +
                "            \"quote_currency\": \"USD\"," +
                "            \"base_min_size\": \"0.001\"," +
                "            \"base_max_size\": \"70\"," +
                "            \"base_increment\": \"0.00000001\"," +
                "            \"quote_increment\": \"0.01\"," +
                "            \"display_name\": \"BTC/USD\"," +
                "            \"status\": \"online\"," +
                "            \"status_message\": \"some status message1\"," +
                "            \"min_market_funds\": \"10\"," +
                "            \"max_market_funds\": \"1000000\"," +
                "            \"post_only\": false," +
                "            \"limit_only\": false," +
                "            \"cancel_only\": false" +
                "        }" +
                "    ]," +
                "    \"currencies\": [" +
                "        {" +
                "            \"id\": \"USD\"," +
                "            \"name\": \"United States Dollar\"," +
                "            \"min_size\": \"0.01000000\"," +
                "            \"status\": \"online\"," +
                "            \"status_message\": \"some status message2\"," +
                "            \"max_precision\": \"0.01\"," +
                "            \"convertible_to\": [\"USDC\"]," +
                "            \"details\": {}" +
                "        }," +
                "        {" +
                "            \"id\": \"USDC\"," +
                "            \"name\": \"USD Coin\"," +
                "            \"min_size\": \"0.00000100\"," +
                "            \"status\": \"online\"," +
                "            \"status_message\": null," +
                "            \"max_precision\": \"0.000001\"," +
                "            \"convertible_to\": [\"USD\"], " +
                "            \"details\": {}" +
                "        }," +
                "        {" +
                "            \"id\": \"BTC\"," +
                "            \"name\": \"Bitcoin\"," +
                "            \"min_size\":\" 0.00000001\"," +
                "            \"status\": \"online\"," +
                "            \"status_message\": null," +
                "            \"max_precision\": \"0.00000001\"," +
                "            \"convertible_to\": []" +
                "        }" +
                "    ]" +
                "}";

        StatusMessage result = objectMapper.readValue(json, StatusMessage.class);

        assertEquals("status", result.getType());

        assertEquals("BTC-USD", result.getProducts()[0].getId());
        assertEquals("BTC", result.getProducts()[0].getBase_currency());
        assertEquals("USD", result.getProducts()[0].getQuote_currency());
        assertEquals(0.001d, result.getProducts()[0].getBase_min_size());
        assertEquals(70d, result.getProducts()[0].getBase_max_size());
        assertEquals(0.00000001d, result.getProducts()[0].getBase_increment());
        assertEquals(0.01d, result.getProducts()[0].getQuote_increment());
        assertEquals("BTC/USD", result.getProducts()[0].getDisplay_name());
        assertEquals("online", result.getProducts()[0].getStatus());
        assertEquals("some status message1", result.getProducts()[0].getStatus_message());
        assertEquals(10, result.getProducts()[0].getMin_market_funds());
        assertEquals(1000000, result.getProducts()[0].getMax_market_funds());
        assertEquals(false, result.getProducts()[0].getPost_only());
        assertEquals(false, result.getProducts()[0].getLimit_only());
        assertEquals(false, result.getProducts()[0].getCancel_only());

        assertEquals("USD", result.getCurrencies()[0].getId());
        assertEquals("United States Dollar", result.getCurrencies()[0].getName());
        assertEquals(new BigDecimal(0.01000000).setScale(8, HALF_UP), result.getCurrencies()[0].getMin_size());
        assertEquals("online", result.getCurrencies()[0].getStatus());
        assertEquals("some status message2", result.getCurrencies()[0].getStatus_message());
        assertEquals(new BigDecimal(0.01).setScale(2, HALF_UP), result.getCurrencies()[0].getMax_precision());
        assertArrayEquals(new String[]{"USDC"}, result.getCurrencies()[0].getConvertible_to());
        assertEquals(Collections.EMPTY_MAP, result.getCurrencies()[0].getDetails());


        assertEquals("USDC", result.getCurrencies()[1].getId());
        assertEquals("USD Coin", result.getCurrencies()[1].getName());
        assertEquals(new BigDecimal(0.00000100).setScale(8, HALF_UP), result.getCurrencies()[1].getMin_size());
        assertEquals("online", result.getCurrencies()[1].getStatus());
        assertEquals(null, result.getCurrencies()[1].getStatus_message());
        assertEquals(new BigDecimal(0.000001).setScale(6, HALF_UP), result.getCurrencies()[1].getMax_precision());
        assertArrayEquals(new String[]{"USD"}, result.getCurrencies()[1].getConvertible_to());
        assertEquals(Collections.EMPTY_MAP, result.getCurrencies()[1].getDetails());

        assertEquals("BTC", result.getCurrencies()[2].getId());
        assertEquals("Bitcoin", result.getCurrencies()[2].getName());
        assertEquals( new BigDecimal(0.00000001).setScale(8, HALF_UP), result.getCurrencies()[2].getMin_size());
        assertEquals("online", result.getCurrencies()[2].getStatus());
        assertEquals(null, result.getCurrencies()[2].getStatus_message());
        assertEquals(new BigDecimal(0.00000001).setScale(8, HALF_UP), result.getCurrencies()[2].getMax_precision());
        assertArrayEquals(new String[]{}, result.getCurrencies()[2].getConvertible_to());
        assertEquals(null, result.getCurrencies()[2].getDetails());
    }
}