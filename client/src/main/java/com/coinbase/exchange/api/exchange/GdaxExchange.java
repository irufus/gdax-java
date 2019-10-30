/**
 * Created by irufus on 2/19/15.
 */
package com.coinbase.exchange.api.exchange;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface GdaxExchange {
    /**
     * @return
     * @throws IOException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws CloneNotSupportedException
     */
    public String getBaseUrl();
    public <R> HttpEntity<String> securityHeaders(String endpoint, String method, String body);
    public <T> T get(String endpoint, ParameterizedTypeReference<T> type);
    public <T> T pagedGet(String endpoint, ParameterizedTypeReference<T> responseType, String beforeOrAfter, Integer pageNumber, Integer limit);
    public <T> List<T> getAsList(String endpoint, ParameterizedTypeReference<T[]> type);
    public <T> List<T> pagedGetAsList(String endpoint, ParameterizedTypeReference<T[]> responseType, String beforeOrAfter, Integer pageNumber, Integer limit);
    public <T, R> T post(String endpoint, ParameterizedTypeReference<T> type, R jsonObject);
    public <T> T delete(String endpoint, ParameterizedTypeReference<T> type);
}
