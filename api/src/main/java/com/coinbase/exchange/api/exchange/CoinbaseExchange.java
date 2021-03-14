package com.coinbase.exchange.api.exchange;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;

import java.util.List;

public interface CoinbaseExchange {

    String getBaseUrl();

    <R> HttpEntity<String> securityHeaders(String endpoint, String method, String body);

    <T> T get(String endpoint, ParameterizedTypeReference<T> type) throws CoinbaseExchangeException;

    <T> T pagedGet(
            String endpoint, ParameterizedTypeReference<T> responseType, String beforeOrAfter, Integer pageNumber,
            Integer limit
    ) throws CoinbaseExchangeException;

    <T> List<T> getAsList(String endpoint, ParameterizedTypeReference<T[]> type) throws CoinbaseExchangeException;

    <T> List<T> pagedGetAsList(
            String endpoint, ParameterizedTypeReference<T[]> responseType, String beforeOrAfter, Integer pageNumber,
            Integer limit
    ) throws CoinbaseExchangeException;

    <T, R> T post(String endpoint, ParameterizedTypeReference<T> type, R jsonObject) throws CoinbaseExchangeException;

    <T> T delete(String endpoint, ParameterizedTypeReference<T> type) throws CoinbaseExchangeException;
}
