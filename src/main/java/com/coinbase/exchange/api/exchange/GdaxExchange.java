/**
 * Created by irufus on 2/19/15.
 */
package com.coinbase.exchange.api.exchange;

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
    <T> T get(String endpoint, Class<T> type);
    <T> T pagedGet(String endpoint, Class<T> type, String beforeOrAfter, Integer pageNumber, Integer limit);
    <T> List<T> getAsList(String endpoint, Class<T[]> type);
    <T> List<T> pagedGetAsList(String endpoint, Class<T[]> type, String beforeOrAfter, Integer pageNumber, Integer limit);
    <T> T post(String endpoint, Class<T> type, String json);
    <T> T delete(String endpoint, Class<T> type);
}
