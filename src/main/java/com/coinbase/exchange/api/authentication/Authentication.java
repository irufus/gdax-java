package com.coinbase.exchange.api.authentication;

import com.coinbase.exchange.api.constants.GdaxConstants;
import com.coinbase.exchange.api.exchange.CoinbaseExchange;
import com.coinbase.exchange.api.exchange.CoinbaseExchangeImpl;
import org.apache.http.client.methods.HttpUriRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;

/**
 * Created by irufus on 2/19/15.
 */
@Component
public class Authentication {

    static String publicKey;
    static String secretKey;
    static String passphrase;

    @Autowired
    public Authentication(@Value("${gdax.key}") String publicKey,
                          @Value("${gdax.secret}") String secretKey,
                          @Value("${gdax.passphrase}") String passphrase) {

        this.publicKey = publicKey;
        this.secretKey = secretKey;
        this.passphrase = passphrase;
    }

    public void setAuthenticationHeaders(HttpUriRequest request, String method, String endpoint_url, String body) throws InvalidKeyException, NoSuchAlgorithmException, CloneNotSupportedException, UnsupportedEncodingException {
        String timestamp = Instant.now().getEpochSecond() + "";
        String signature = generateSignature(timestamp, method, endpoint_url, body);

        request.addHeader("accept", "application/json");
        request.addHeader("CB-ACCESS-KEY", publicKey);
        request.addHeader("CB-ACCESS-SIGN", signature);
        request.addHeader("CB-ACCESS-TIMESTAMP", timestamp);
        request.addHeader("CB-ACCESS-PASSPHRASE", passphrase);
    }
    public void setAuthenticationHeaders(HttpUriRequest request, String method, String endpoint_url) throws NoSuchAlgorithmException, InvalidKeyException, CloneNotSupportedException, UnsupportedEncodingException {
        setAuthenticationHeaders(request, method, endpoint_url, "");
    }
    public static String generateSignature(String timestamp, String method, String endpoint_url, String body) throws NoSuchAlgorithmException, InvalidKeyException, CloneNotSupportedException {
        String prehash = timestamp + method.toUpperCase() + endpoint_url + body;
        byte[] secretDecoded = Base64.getDecoder().decode(secretKey);
        SecretKeySpec keyspec = new SecretKeySpec(secretDecoded, "HmacSHA256");
        Mac sha256 = (Mac) GdaxConstants.SHARED_MAC.clone();
        sha256.init(keyspec);
        return Base64.getEncoder().encodeToString(sha256.doFinal(prehash.getBytes()));
    }
    public static void setNonAuthenticationHeaders(HttpUriRequest request)
    {
        request.addHeader("accept", "application/json");
    }
}
