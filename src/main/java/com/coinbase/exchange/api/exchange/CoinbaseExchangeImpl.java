package com.coinbase.exchange.api.exchange;

import com.coinbase.exchange.api.constants.GdaxConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.time.Instant;
import java.util.Base64;


/**
 * Created by irufus on 2/25/15.
 */
@Component
public class CoinbaseExchangeImpl implements CoinbaseExchange {

    String publicKey;
    String secretKey;
    String passphrase;
    String baseUrl;

    @Autowired
    public CoinbaseExchangeImpl(@Value("${gdax.key}") String publicKey,
                                @Value("${gdax.secret}") String secretKey,
                                @Value("${gdax.passphrase}") String passphrase,
                                @Value("${gdax.api.baseUrl}") String baseUrl) {
        this.publicKey = publicKey;
        this.secretKey = secretKey;
        this.passphrase = passphrase;
        this.baseUrl = baseUrl;
    }

    @Override
    public String getBaseUrl() {
        return baseUrl;
    }

    @Override
    public HttpEntity<String> securityHeaders(String endpoint, String method, String body) throws InvalidKeyException, CloneNotSupportedException {
        HttpHeaders headers = new HttpHeaders();
        String timestamp = Instant.now().getEpochSecond() + "";

        headers.add("accept", "application/json");
        headers.add("content-type", "application/json");
        headers.add("CB-ACCESS-KEY", publicKey);
        headers.add("CB-ACCESS-SIGN", generateSignature(endpoint, method, body, timestamp));
        headers.add("CB-ACCESS-TIMESTAMP", timestamp);
        headers.add("CB-ACCESS-PASSPHRASE", passphrase);

        return new HttpEntity<String>(headers);
    }

    @Override
    public String generateSignature(String resource, String method, String body, String timestamp) throws CloneNotSupportedException, InvalidKeyException {
        String prehash = timestamp + method.toUpperCase() + resource + body;
        byte[] secretDecoded = Base64.getDecoder().decode(secretKey);
        SecretKeySpec keyspec = new SecretKeySpec(secretDecoded, "HmacSHA256");
        Mac sha256 = (Mac) GdaxConstants.SHARED_MAC.clone();
        sha256.init(keyspec);
        return Base64.getEncoder().encodeToString(sha256.doFinal(prehash.getBytes()));
    }
}
