package org.irufus.coinbaseexc.api;

import org.apache.http.client.methods.HttpGet;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;

/**
 * Created by irufus on 2/19/15.
 */
public class Authentication {
    
    private String publicKey;
    private String passphrase;
    private String secretKey;
    
    public Authentication(String public_key, String secret_key, String passphrase){
        this.publicKey = public_key;
        this.passphrase = passphrase;
        this.secretKey = secret_key;
    }
    public void setAuthenticationHeaders(HttpGet getRequest, String endpoint_url, String body) throws InvalidKeyException, NoSuchAlgorithmException, CloneNotSupportedException {
        String method = "GET";
        String timestamp = Instant.now().getEpochSecond() + "";
        String signature = generateSignature(secretKey, timestamp, method, endpoint_url, body);
        
        getRequest.addHeader("accept", "application/json");
        getRequest.addHeader("CB-ACCESS-KEY", publicKey);
        getRequest.addHeader("CB-ACCESS-SIGN", signature);
        getRequest.addHeader("CB-ACCESS-TIMESTAMP", timestamp);
        getRequest.addHeader("CB-ACCESS-PASSPHRASE", passphrase);
    }
    public void setAuthenticationHeaders(HttpGet getRequest, String endpoint_url) throws NoSuchAlgorithmException, InvalidKeyException, CloneNotSupportedException {
        setAuthenticationHeaders(getRequest, endpoint_url, "");
    }
    public static String generateSignature(String secret_key, String timestamp, String method, String endpoint_url, String body) throws NoSuchAlgorithmException, InvalidKeyException, CloneNotSupportedException {
        String prehash = timestamp + method.toUpperCase() + endpoint_url + body;
        byte[] secretDecoded = Base64.getDecoder().decode(secret_key);
        SecretKeySpec keyspec = new SecretKeySpec(secretDecoded, "HmacSHA256");
        Mac sha256 = (Mac) CoinbaseExchangeImpl.SHARED_MAC.clone();
        sha256.init(keyspec);
        return Base64.getEncoder().encodeToString(sha256.doFinal(prehash.getBytes()));
    }
}
