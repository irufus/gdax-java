/**
 * Created by irufus on 2/19/15.
 */
package org.irufus.coinbaseexc.api;

import com.google.gson.Gson;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.irufus.coinbaseexc.api.entity.Account;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class CoinbaseExchange {
    
    private Authentication auth;
    private static final String cbURL = "https://api.exchange.coinbase.com";
    
    public CoinbaseExchange(String public_key, String secret_key, String passphrase){
        auth = new Authentication(public_key, secret_key, passphrase);
    }
    
    public Account[] getAccounts(){
        String endpoint = "/accounts";
        HttpGet getRequest = new HttpGet(cbURL + endpoint);
        
        try {
            auth.setAuthenticationHeaders(getRequest, endpoint);
            CloseableHttpResponse response = HttpClients.createDefault().execute(getRequest);
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String json = processStream(br);
            Gson gson = new Gson();
            Account[] accounts = gson.fromJson(json, Account[].class );
            return accounts;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private String processStream(BufferedReader br) throws IOException
    {
        String json = "";
        String output = null;
        while((output = br.readLine()) != null)
            json += output;
        return json;
    }
}
