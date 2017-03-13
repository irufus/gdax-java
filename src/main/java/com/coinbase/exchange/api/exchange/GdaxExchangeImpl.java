package com.coinbase.exchange.api.exchange;

import com.coinbase.exchange.api.constants.GdaxConstants;
import com.coinbase.exchange.api.gui.orderbook.OrderBookView;
import com.coinbase.exchange.api.util.WebsocketFeed;
import com.coinbase.exchange.api.websocketfeed.message.Subscribe;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.management.RuntimeErrorException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.time.Instant;
import java.util.Base64;

import static org.springframework.http.HttpMethod.GET;


/**
 * Created by irufus on 2/25/15.
 */
@Component
public class GdaxExchangeImpl implements GdaxExchange {

    static Logger log = Logger.getLogger(GdaxExchangeImpl.class.getName());

    String publicKey;
    String secretKey;
    String passphrase;
    String baseUrl;
    String socketfeed;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    public GdaxExchangeImpl(@Value("${gdax.key}") String publicKey,
                            @Value("${gdax.secret}") String secretKey,
                            @Value("${gdax.passphrase}") String passphrase,
                            @Value("${gdax.api.baseUrl}") String baseUrl,
                            @Value("${websocket.baseUrl}") String socketfeed) {
        this.publicKey = publicKey;
        this.secretKey = secretKey;
        this.passphrase = passphrase;
        this.baseUrl = baseUrl;
        this.socketfeed = socketfeed;
    }

    @Override
    public <T> T get(String resourcePath, ParameterizedTypeReference<T> responseType) {
        try {
            ResponseEntity<T> responseEntity = restTemplate.exchange(getBaseUrl() + resourcePath,
                    GET,
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
    public <T> T pagedGet(String resourcePath,
                          ParameterizedTypeReference<T> responseType,
                          String beforeOrAfter,
                          Integer pageNumber,
                          Integer limit) {
        resourcePath += "?" + beforeOrAfter + "=" + pageNumber + "&limit=" + limit;
        return get(resourcePath, responseType);
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
    public void websocketFeed(Subscribe message) {

        Gson gson = new Gson();
        String jsonSubscribeMessage = gson.toJson(message);

        try {
            final WebsocketFeed clientEndPoint = new WebsocketFeed(new URI(socketfeed));

            // add listener
            clientEndPoint.addMessageHandler(new WebsocketFeed.MessageHandler() {
                @Autowired
                OrderBookView orderBookView;

                public void handleMessage(String msg) {
                    orderBookView.newTrade(msg);
                }
            });
            // send message to websocket
            clientEndPoint.sendMessage(jsonSubscribeMessage);

            Thread.sleep(1000);

        } catch (InterruptedException ex) {
            System.err.println("InterruptedException exception: " + ex.getMessage());
        } catch (URISyntaxException ex) {
            System.err.println("URISyntaxException exception: " + ex.getMessage());
        }
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
        headers.add("CB-ACCESS-KEY", publicKey);
        headers.add("CB-ACCESS-SIGN", generateSignature(resource, method, jsonBody, timestamp));
        headers.add("CB-ACCESS-TIMESTAMP", timestamp);
        headers.add("CB-ACCESS-PASSPHRASE", passphrase);

        curlRequest(method, jsonBody, headers, resource);

        return new HttpEntity<>(jsonBody, headers);
    }

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

    /**
     * The CB-ACCESS-SIGN header is generated by creating a sha256 HMAC using
     * the base64-decoded secret key on the prehash string for:
     * timestamp + method + requestPath + body (where + represents string concatenation)
     * and base64-encode the output.
     * The timestamp value is the same as the CB-ACCESS-TIMESTAMP header.
     * @param requestPath
     * @param method
     * @param body
     * @param timestamp
     * @return
     */
    @Override
    public String generateSignature(String requestPath, String method, String body, String timestamp) {
        try {
            String prehash = timestamp + method.toUpperCase() + requestPath + body;
            byte[] secretDecoded = Base64.getDecoder().decode(secretKey);
            SecretKeySpec keyspec = new SecretKeySpec(secretDecoded, "HmacSHA256");
            Mac sha256 = (Mac) GdaxConstants.SHARED_MAC.clone();
            sha256.init(keyspec);
            return Base64.getEncoder().encodeToString(sha256.doFinal(prehash.getBytes()));
        } catch (CloneNotSupportedException | InvalidKeyException e) {
            e.printStackTrace();
            throw new RuntimeErrorException(new Error("Cannot set up authentication headers."));
        }
    }
}
