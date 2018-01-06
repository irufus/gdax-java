package com.coinbase.exchange.api.exchange;

import com.coinbase.exchange.api.config.GdaxInstanceVariables;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;


/**
 * Created by irufus on 2/25/15.
 */
public class GdaxExchangeImpl implements GdaxExchange {

    static Logger log = LoggerFactory.getLogger(GdaxExchangeImpl.class);

    private String publicKey;
    private String passphrase;
    private String baseUrl;

    Gson gson = new Gson();


    public GdaxExchangeImpl(Signature signature) {
        this.publicKey = GdaxInstanceVariables.key;
        this.passphrase = GdaxInstanceVariables.passphrase;
        this.baseUrl = GdaxInstanceVariables.baseUrl;
    }

    @Override
    public <T> T get(String resourcePath, Class<T> responseType) {
        GdaxReturnValue value = makeRequest(resourcePath, "GET", "");
        return gson.fromJson(value.getGdaxValue(), responseType);
    }

    @Override
    public <T> List<T> getAsList(String resourcePath, Class<T[]> responseType) {
        GdaxReturnValue value = makeRequest(resourcePath, "GET", "");
        T[] result = gson.fromJson(value.getGdaxValue(), responseType);
        return result == null ? Arrays.asList() : Arrays.asList(result);
    }

    @Override
    public <T> T pagedGet(String resourcePath,
                          Class<T> responseType,
                          String beforeOrAfter,
                          Integer pageNumber,
                          Integer limit) {
        resourcePath += "?" + beforeOrAfter + "=" + pageNumber + "&limit=" + limit;
        return get(resourcePath, responseType);
    }

    @Override
    public <T> List<T> pagedGetAsList(String resourcePath,
                                      Class<T[]> responseType,
                                      String beforeOrAfter,
                                      Integer pageNumber,
                                      Integer limit) {
        T[] result = pagedGet(resourcePath, responseType, beforeOrAfter, pageNumber, limit );
        return result == null ? Arrays.asList() : Arrays.asList(result);
    }

    @Override
    public <T> T delete(String resourcePath, Class<T> responseType) {
        GdaxReturnValue value = makeRequest(resourcePath, "DELETE", "");
        return gson.fromJson(value.getGdaxValue(), responseType);
    }

    @Override
    public <T> T post(String resourcePath,  Class<T> responseType, String jsonObj) {
        GdaxReturnValue value = makeRequest(resourcePath, "POST", jsonObj);
        return gson.fromJson(value.getGdaxValue(), responseType);
    }

    public GdaxReturnValue makeRequest(String resourcePath, String method, String jsonBody) {
        GdaxReturnValue retVal = null;
        String timestamp = new Long(Instant.now().getEpochSecond()).toString();
        URL url = null;
        HttpURLConnection conn = null;
        boolean isPost = method.equals("POST");
        try {
            url = new URL(this.baseUrl + resourcePath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            log.error("POST request Failed for '" + resourcePath);
        }
        conn.setDoInput(true);
        conn.setRequestProperty("accept", "application/json");
        conn.setRequestProperty("content-type", "application/json");
        conn.setRequestProperty("CB-ACCESS-KEY", publicKey);
        conn.setRequestProperty("CB-ACCESS-SIGN", Signature.generate(resourcePath, method, jsonBody, timestamp));
        conn.setRequestProperty("CB-ACCESS-TIMESTAMP", timestamp);
        conn.setRequestProperty("CB-ACCESS-PASSPHRASE", passphrase);
        if(isPost) {
            conn.setDoOutput(true);
            try {
                conn.connect();
                OutputStreamWriter sender = new OutputStreamWriter(conn.getOutputStream());
                sender.write(jsonBody);
                sender.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            if (!isPost) {
                conn.connect();
            }
            String json = createStringFromCharStream(new InputStreamReader(conn.getInputStream()));
            retVal = new GdaxReturnValue(json,
                    conn.getResponseCode(),
                    conn.getHeaderFields());
        } catch (IOException e) {
            e.printStackTrace();
        }
        conn.disconnect();
        return retVal;
    }

    String createStringFromCharStream(InputStreamReader isr) throws IOException {
        CharArrayWriter caw = new CharArrayWriter();
        char[] buff = new char[512];
        int len;
        while ((len = isr.read(buff)) != -1) {
            caw.write(buff, 0, len);
        }
        String str = caw.toString();
        caw.close();
        isr.close();
        return str;
    }
}
