package com.coinbase.exchange.api.exchange;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;

import java.util.List;

public interface CoinbasePro {

    public String getBaseUrl();
    public <R> HttpEntity<String> securityHeaders(String endpoint, String method, String body);
    public <T> T get(String endpoint, ParameterizedTypeReference<T> type);
    public <T> T pagedGet(String endpoint, ParameterizedTypeReference<T> responseType, String beforeOrAfter, Integer pageNumber, Integer limit);
    public <T> List<T> getAsList(String endpoint, ParameterizedTypeReference<T[]> type);
    public <T> List<T> pagedGetAsList(String endpoint, ParameterizedTypeReference<T[]> responseType, String beforeOrAfter, Integer pageNumber, Integer limit);
    public <T, R> T post(String endpoint, ParameterizedTypeReference<T> type, R jsonObject);
    public <T> T delete(String endpoint, ParameterizedTypeReference<T> type);
}
