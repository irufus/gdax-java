package com.coinbase.exchange.api.exchange;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

/**
 * This class acts as a central point for providing user configuration and making GET/POST/PUT requests as well as
 * getting responses as Lists of objects rather than arrays.
 */
public class CoinbaseExchangeImpl implements CoinbaseExchange {

    static final Logger log = LoggerFactory.getLogger(CoinbaseExchangeImpl.class.getName());

    final String publicKey;
    final String passphrase;
    final String baseUrl;
    final Signature signature;
    final RestTemplate restTemplate;

    public CoinbaseExchangeImpl(final String publicKey,
                                final String passphrase,
                                final String baseUrl,
                                final Signature signature,
                                final RestTemplate restTemplate) {
        this.publicKey = publicKey;
        this.passphrase = passphrase;
        this.baseUrl = baseUrl;
        this.signature = signature;
        this.restTemplate = restTemplate;
    }

    @Override
    public <T> T get(String resourcePath, ParameterizedTypeReference<T> responseType) {
        try {
            ResponseEntity<T> responseEntity = restTemplate.exchange(getBaseUrl() + resourcePath,
                    HttpMethod.GET,
                    securityHeaders(resourcePath,
                    "GET",
                     ""),
                    responseType);
            return responseEntity.getBody();
        } catch (HttpClientErrorException ex) {
            log.error("GET request Failed for '" + resourcePath + "': " + ex.getResponseBodyAsString());
        }
        return null;
    }

    @Override
    public <T> List<T> getAsList(String resourcePath, ParameterizedTypeReference<T[]> responseType) {
       T[] result = get(resourcePath, responseType);

       return result == null ? Arrays.asList() : Arrays.asList(result);
    }

    @Override
    public <T> T pagedGet(String resourcePath,
                          ParameterizedTypeReference<T> responseType,
                          String beforeOrAfter,
                          Integer pageNumber,
                          Integer limit) {
        resourcePath += "?" + beforeOrAfter + "=" + pageNumber + "&limit=" + limit;
        return get(resourcePath, responseType);
    }

    @Override
    public <T> List<T> pagedGetAsList(String resourcePath,
                          ParameterizedTypeReference<T[]> responseType,
                          String beforeOrAfter,
                          Integer pageNumber,
                          Integer limit) {
        T[] result = pagedGet(resourcePath, responseType, beforeOrAfter, pageNumber, limit );
        return result == null ? Arrays.asList() : Arrays.asList(result);
    }

    @Override
    public <T> T delete(String resourcePath, ParameterizedTypeReference<T> responseType) {
        try {
            ResponseEntity<T> response = restTemplate.exchange(getBaseUrl() + resourcePath,
                HttpMethod.DELETE,
                securityHeaders(resourcePath, "DELETE", ""),
                responseType);
            return response.getBody();
        } catch (HttpClientErrorException ex) {
            log.error("DELETE request Failed for '" + resourcePath + "': " + ex.getResponseBodyAsString());
        }
        return null;
    }

    @Override
    public <T, R> T post(String resourcePath,  ParameterizedTypeReference<T> responseType, R jsonObj) {
        Gson gson = new Gson();
        String jsonBody = gson.toJson(jsonObj);

        try {
            ResponseEntity<T> response = restTemplate.exchange(getBaseUrl() + resourcePath,
                    HttpMethod.POST,
                    securityHeaders(resourcePath, "POST", jsonBody),
                    responseType);
            return response.getBody();
        } catch (HttpClientErrorException ex) {
            log.error("POST request Failed for '" + resourcePath + "': " + ex.getResponseBodyAsString());
        }
        return null;
    }

    @Override
    public String getBaseUrl() {
        return baseUrl;
    }

    @Override
    public HttpEntity<String> securityHeaders(String endpoint, String method, String jsonBody) {
        HttpHeaders headers = new HttpHeaders();

        String timestamp = Instant.now().getEpochSecond() + "";
        String resource = endpoint.replace(getBaseUrl(), "");

        headers.add("accept", "application/json");
        headers.add("content-type", "application/json");
        headers.add("User-Agent", "gdax-java unofficial coinbase pro api library");
        headers.add("CB-ACCESS-KEY", publicKey);
        headers.add("CB-ACCESS-SIGN", signature.generate(resource, method, jsonBody, timestamp));
        headers.add("CB-ACCESS-TIMESTAMP", timestamp);
        headers.add("CB-ACCESS-PASSPHRASE", passphrase);

        curlRequest(method, jsonBody, headers, resource);

        return new HttpEntity<>(jsonBody, headers);
    }

    /**
     * Purely here for logging an equivalent curl request for debugging
     * note that the signature is time-sensitive and has a time to live of about 1 minute after which the request
     * is no longer valid.
     * @param method
     * @param jsonBody
     * @param headers
     * @param resource
     */
    private void curlRequest(String method, String jsonBody, HttpHeaders headers, String resource) {
        String curlTest = "curl ";
        for (String key : headers.keySet()){
            curlTest +=  "-H '" + key + ":" + headers.get(key).get(0) + "' ";
        }
        if (!jsonBody.equals(""))
            curlTest += "-d '" + jsonBody + "' ";

        curlTest += "-X " + method + " " + getBaseUrl() + resource;
        log.debug(curlTest);
    }
}
