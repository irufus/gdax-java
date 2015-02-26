package org.irufus.coinbaseexc.api;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;

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
public class Authentication {
    
    private String publicKey;
    private String passphrase;
    private String secretKey;
    
    public Authentication(String public_key, String secret_key, String passphrase){
        this.publicKey = public_key;
        this.passphrase = passphrase;
        this.secretKey = secret_key;
    }
    public void setAuthenticationHeaders(HttpUriRequest request, String method, String endpoint_url, String body) throws InvalidKeyException, NoSuchAlgorithmException, CloneNotSupportedException, UnsupportedEncodingException {
        String timestamp = Instant.now().getEpochSecond() + "";
        String signature = generateSignature(secretKey, timestamp, method, endpoint_url, body);

        request.addHeader("accept", "application/json");
        request.addHeader("CB-ACCESS-KEY", publicKey);
        request.addHeader("CB-ACCESS-SIGN", signature);
        request.addHeader("CB-ACCESS-TIMESTAMP", timestamp);
        request.addHeader("CB-ACCESS-PASSPHRASE", passphrase);
    }
    public void setAuthenticationHeaders(HttpUriRequest request, String method, String endpoint_url) throws NoSuchAlgorithmException, InvalidKeyException, CloneNotSupportedException, UnsupportedEncodingException {
        setAuthenticationHeaders(request, method, endpoint_url, "");
    }
    public static String generateSignature(String secret_key, String timestamp, String method, String endpoint_url, String body) throws NoSuchAlgorithmException, InvalidKeyException, CloneNotSupportedException {
        String prehash = timestamp + method.toUpperCase() + endpoint_url + body;
        byte[] secretDecoded = Base64.getDecoder().decode(secret_key);
        SecretKeySpec keyspec = new SecretKeySpec(secretDecoded, "HmacSHA256");
        Mac sha256 = (Mac) CoinbaseExchangeImpl.SHARED_MAC.clone();
        sha256.init(keyspec);
        return Base64.getEncoder().encodeToString(sha256.doFinal(prehash.getBytes()));
    }
    public static void setNonAuthenticationHeaders(HttpUriRequest request)
    {
        request.addHeader("accept", "application/json");
    }
}
