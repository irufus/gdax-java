/**
 * Created by irufus on 2/19/15.
 */
package com.coinbase.exchange.api.exchange;

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
    public <T> T get(String endpoint, Class<T> type);
    public <T> T pagedGet(String endpoint, Class<T> responseType, String beforeOrAfter, Integer pageNumber, Integer limit);
    public <T> List<T> getAsList(String endpoint, Class<T[]> type);
    public <T> List<T> pagedGetAsList(String endpoint, Class<T[]> responseType, String beforeOrAfter, Integer pageNumber, Integer limit);
    public <T, R> T post(String endpoint, Class<T> type, R jsonObject);
    public <T> T delete(String endpoint, Class<T> type);
}
