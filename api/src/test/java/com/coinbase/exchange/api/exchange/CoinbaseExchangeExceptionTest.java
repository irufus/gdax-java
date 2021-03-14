package com.coinbase.exchange.api.exchange;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.coinbase.exchange.api.config.IntegrationTestConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.HttpClientErrorException;

@JsonTest
@ContextConfiguration(classes = IntegrationTestConfiguration.class)
class CoinbaseExchangeExceptionTest {
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void createBuildInstanceWithMessage() {
        HttpClientErrorException httpException = mock(HttpClientErrorException.class);

        when(httpException.getResponseBodyAsString()).thenReturn("{\"message\": \"error\"}");

        CoinbaseExchangeException result = CoinbaseExchangeException.create(httpException, objectMapper);

        assertAll(
                () -> assertThat(result.getMessage(), is(equalTo("error"))),
                () -> assertThat(result.getCause(), is(sameInstance(httpException)))
        );
    }

    @Test
    void createBuildInstanceWithoutMessageWhenInvalid() {
        HttpClientErrorException httpException = mock(HttpClientErrorException.class);

        when(httpException.getResponseBodyAsString()).thenReturn("{\"message\": error\"}");

        CoinbaseExchangeException result = CoinbaseExchangeException.create(httpException, objectMapper);

        assertAll(
                () -> assertThat(result.getMessage(), is(nullValue())),
                () -> assertThat(result.getCause(), is(sameInstance(httpException)))
        );
    }
}
