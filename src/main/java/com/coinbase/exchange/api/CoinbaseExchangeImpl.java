package com.coinbase.exchange.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import com.coinbase.exchange.api.entity.*;

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
    public AccountHistory[] getAccountHistory(String accountid) throws CloneNotSupportedException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        Gson gson = new Gson();
        String endpoint = "/accounts/" + accountid + "/ledger";
        String json = generateGetRequestJSON(endpoint);
        return gson.fromJson(json, AccountHistory[].class);
    }

    @Override
    public Hold[] getHolds(String accountid) throws CloneNotSupportedException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        Gson gson = new Gson();
        String endpoint = "/accounts/" + accountid + "/holds";
        String json = generateGetRequestJSON(endpoint);
        return gson.fromJson(json, Hold[].class);
    }

    @Override
    public Order createOrder(NewOrderSingle order) throws CloneNotSupportedException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        Gson gson = new Gson();
        String body = gson.toJson(order);
        String json = generatePostRequestJSON("/orders", body);
        System.out.println(json);
        return gson.fromJson(json, Order.class);
    }

    @Override
    public String cancelOrder(String orderid) throws CloneNotSupportedException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        return executeDeleteRequest("/orders", orderid);
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
    public Order getOrder(String order_id) throws CloneNotSupportedException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        String endpoint = "/orders/" + order_id;
        String json = generateGetRequestJSON(endpoint);
        Gson gson = new Gson();
        return gson.fromJson(json, Order.class);
    }

    @Override
    public Product[] getProducts() throws IOException {
        String endpoint = "/products";
        HttpGet getRequest = new HttpGet(cbURL + endpoint);
        Authentication.setNonAuthenticationHeaders(getRequest);
        CloseableHttpResponse response = HttpClients.createDefault().execute(getRequest);
        BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String json = processStream(br);
        Gson gson = new Gson();
        Product[] products = gson.fromJson(json, Product[].class);
        return products;
    }

    @Override
    public Fill[] getFills() {
        return new Fill[0];
    }

    @Override
    public String getMarketDataOrderBook(String product, String level) throws IOException {
        String endpoint = "/products/" + product + "/book";
        if(level != null)
            endpoint += "?level=" + level;
        HttpGet getRequest = new HttpGet(cbURL + endpoint);
        Authentication.setNonAuthenticationHeaders(getRequest);
        CloseableHttpResponse response = HttpClients.createDefault().execute(getRequest);
        BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String json = processStream(br);
        return json;
    }
    /*
    @Override
    public ProductOrderBook getMarketDataOrderBookProduct(String product, String level) throws IOException{
        String json = getMarketDataOrderBook(product, level);
        Gson gson = new Gson();
        ProductOrderBook pob = gson.fromJson(json, ProductOrderBook.class);
        return pob;
    }*/

    private String executeDeleteRequest(String endpoint, String parameter) throws NoSuchAlgorithmException, InvalidKeyException, CloneNotSupportedException, IOException {
        HttpDelete deleteRequest = new HttpDelete(cbURL + endpoint + "/" + parameter);
        auth.setAuthenticationHeaders(deleteRequest, "DELETE", endpoint + "/" + parameter);
        CloseableHttpResponse response = HttpClients.createDefault().execute(deleteRequest);
        BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        return processStream(br);
    }
    private String generatePostRequestJSON(String endpoint, String body) throws NoSuchAlgorithmException, InvalidKeyException, CloneNotSupportedException, IOException {
        HttpPost postRequest = new HttpPost(cbURL + endpoint);
        auth.setAuthenticationHeaders(postRequest, "POST", endpoint, body);
        postRequest.addHeader("content-type", "application/json");
        postRequest.setEntity(new StringEntity(body));
        CloseableHttpResponse response = HttpClients.createDefault().execute(postRequest);
        BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        return processStream(br);
    }
    private String generateGetRequestJSON(String endpoint) throws NoSuchAlgorithmException, InvalidKeyException, CloneNotSupportedException, IOException {
        HttpGet getRequest = new HttpGet(cbURL + endpoint);
        auth.setAuthenticationHeaders(getRequest, "GET", endpoint);
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
