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
import org.irufus.coinbaseexc.api.entity.AccountHistory;
import org.irufus.coinbaseexc.api.entity.Hold;
import org.irufus.coinbaseexc.api.entity.Order;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public interface CoinbaseExchange {
    /**
     * * 
     * @return
     * @throws IOException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws CloneNotSupportedException
     */
    public Account[] getAccounts() throws IOException, InvalidKeyException, NoSuchAlgorithmException, CloneNotSupportedException;
    
    public Account getAccount(String accountid);
    public AccountHistory[] getAccountHistory(String accountid);
    public Hold[] getHolds(String accountid);
    public String cancelOrder(String orderid);
    public Order[] getOpenOrders() throws NoSuchAlgorithmException, InvalidKeyException, CloneNotSupportedException, IOException;
    public Order getOrder(String order_id);
}
