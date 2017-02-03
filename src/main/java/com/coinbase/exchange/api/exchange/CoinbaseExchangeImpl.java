package com.coinbase.exchange.api.exchange;

import com.coinbase.exchange.api.accounts.AccountsService;
import com.coinbase.exchange.api.authentication.Authentication;
import com.coinbase.exchange.api.entity.*;
import com.coinbase.exchange.api.orders.OrderService;
import com.google.gson.Gson;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


/**
 * Created by irufus on 2/25/15.
 */
@Component
public class CoinbaseExchangeImpl implements CoinbaseExchange {

    private String baseUrl;

    @Autowired
    private Authentication auth;

    @Autowired
    AccountsService accountService;

    @Autowired
    OrderService orderService;

    @Autowired
    public CoinbaseExchangeImpl(@Value("${gdax.api.baseUrl}") String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public Account getAccount(String accountId) {
        return accountService.getAccount(accountId);
    }

    @Override
    public Account[] getAccounts() throws IOException, InvalidKeyException, NoSuchAlgorithmException, CloneNotSupportedException {
       return accountService.getAccounts();
    }

    @Override
    public AccountHistory[] getAccountHistory(String accountid) throws CloneNotSupportedException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        String endpoint = "/accounts/" + accountid + "/ledger";
        Gson gson = new Gson();
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
    public Order[] getOpenOrders() throws NoSuchAlgorithmException, InvalidKeyException, CloneNotSupportedException, IOException {
        return orderService.getOpenOrders();
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
        HttpGet getRequest = new HttpGet(baseUrl + endpoint);
        Authentication.setNonAuthenticationHeaders(getRequest);
        String json = getResponse(getRequest);
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
        HttpGet getRequest = new HttpGet(baseUrl + endpoint);
        Authentication.setNonAuthenticationHeaders(getRequest);
        return getResponse(getRequest);
    }

    /**
     *
     * @param product
     * @param level
     * @return ProductOrderBook
     * @throws IOException
     */
    @Override
    public ProductOrderBook getMarketDataProductOrderBook(String product, String level) throws IOException {
        String json = getMarketDataOrderBook(product, level);
        System.out.println(json);
        Gson gson = new Gson();
        ProductOrderBook pob = gson.fromJson(json, ProductOrderBook.class);
        return pob;
    }

    public String executeDeleteRequest(String parameter) throws NoSuchAlgorithmException, InvalidKeyException, CloneNotSupportedException, IOException {
        HttpDelete deleteRequest = new HttpDelete(baseUrl + "orders/" + parameter);
        auth.setAuthenticationHeaders(deleteRequest, "DELETE", baseUrl + "/" + parameter);
        return getResponse(deleteRequest);
    }

    public String generatePostRequestJSON(String endpoint, String body) {
        try {
            HttpPost postRequest = new HttpPost(baseUrl + endpoint);
            auth.setAuthenticationHeaders(postRequest, "POST", endpoint, body);
            postRequest.addHeader("content-type", "application/json");
            postRequest.setEntity(new StringEntity(body));
            return getResponse(postRequest);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        } catch (InvalidKeyException ex) {
            ex.printStackTrace();
        } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public String generateGetRequestJSON(String endpoint){
        HttpGet getRequest = new HttpGet(baseUrl + endpoint);
        try {
            auth.setAuthenticationHeaders(getRequest, "GET", endpoint);
            return getResponse(getRequest);
        } catch (NoSuchAlgorithmException nsaEx) {
            nsaEx.printStackTrace();
        } catch (InvalidKeyException ikEx) {
            ikEx.printStackTrace();
        } catch (CloneNotSupportedException cnsEx) {
            cnsEx.printStackTrace();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
        return "";
    }

    public String processStream(BufferedReader br) {
        String json = "";
        String output = null;
        try {
            while ((output = br.readLine()) != null)
                json += output;
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
        return json;
    }

    public String getResponse(HttpUriRequest request) throws IOException {
        CloseableHttpResponse response = HttpClients.createDefault().execute(request);
        BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        return processStream(br);
    }
}
