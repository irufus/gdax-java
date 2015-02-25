package org.irufus.coinbaseexc.api;

import com.google.gson.Gson;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.irufus.coinbaseexc.api.entity.Account;
import org.irufus.coinbaseexc.api.entity.AccountHistory;
import org.irufus.coinbaseexc.api.entity.Hold;
import org.irufus.coinbaseexc.api.entity.Order;

import javax.crypto.Mac;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by irufus on 2/25/15.
 */
public class CoinbaseExchangeImpl implements CoinbaseExchange {
    private Authentication auth;
    private String cbURL;
    protected static Mac SHARED_MAC;

    public CoinbaseExchangeImpl(CoinbaseExchangeBuilder builder) throws NoSuchAlgorithmException {
        auth = new Authentication(builder.public_key, builder.secret_key, builder.passphrase);
        this.cbURL = builder.url;
        SHARED_MAC = Mac.getInstance("HmacSHA256");
    }

    @Override
    public Account[] getAccounts() throws IOException, InvalidKeyException, NoSuchAlgorithmException, CloneNotSupportedException {
        String endpoint = "/accounts";
        String json = generateGetRequestJSON(endpoint);
        Gson gson = new Gson();
        Account[] accounts = gson.fromJson(json, Account[].class );
        return accounts;
    }

    @Override
    public Account getAccount(String accountid) {
        return null;
    }

    @Override
    public AccountHistory[] getAccountHistory(String accountid) {
        return new AccountHistory[0];
    }

    @Override
    public Hold[] getHolds(String accountid) {
        return new Hold[0];
    }

    @Override
    public String cancelOrder(String orderid) {
        return null;
    }

    @Override
    public Order[] getOpenOrders() throws NoSuchAlgorithmException, InvalidKeyException, CloneNotSupportedException, IOException {
        String endpoint = "/orders";
        String json = generateGetRequestJSON(endpoint);
        Gson gson = new Gson();
        Order[] orders = gson.fromJson(json, Order[].class);
        return orders;
    }

    @Override
    public Order getOrder(String order_id) {
        return null;
    }
    private String generateGetRequestJSON(String endpoint) throws NoSuchAlgorithmException, InvalidKeyException, CloneNotSupportedException, IOException {
        HttpGet getRequest = new HttpGet(cbURL + endpoint);
        auth.setAuthenticationHeaders(getRequest, endpoint);
        CloseableHttpResponse response = HttpClients.createDefault().execute(getRequest);
        BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        return processStream(br);
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
