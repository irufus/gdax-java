/**
 * Created by irufus on 2/19/15.
 */
package com.coinbase.exchange.api.exchange;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public interface CoinbaseExchange {
    /**
     * @return
     * @throws IOException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws CloneNotSupportedException
     */
    public String getBaseUrl();
    public <R> HttpEntity<String> securityHeaders(String endpoint, String method, String body);
    public String generateSignature(String timestamp, String method, String endpoint_url, String body);
    public <T> T get(String endpoint, ParameterizedTypeReference<T> type);
    public <T, R> T post(String endpoint, ParameterizedTypeReference<T> type, R jsonObject);
    public <T> T delete(String endpoint, ParameterizedTypeReference<T> type);
}
