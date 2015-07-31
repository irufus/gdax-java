/**
 * Created by irufus on 2/19/15.
 */
package com.coinbase.exchange.api;

import com.coinbase.exchange.api.entity.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


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
    public AccountHistory[] getAccountHistory(String accountid) throws CloneNotSupportedException, NoSuchAlgorithmException, InvalidKeyException, IOException;
    public Hold[] getHolds(String accountid) throws CloneNotSupportedException, NoSuchAlgorithmException, InvalidKeyException, IOException;
    public Order createOrder(NewOrderSingle order) throws CloneNotSupportedException, NoSuchAlgorithmException, InvalidKeyException, IOException;
    public String cancelOrder(String orderid) throws CloneNotSupportedException, NoSuchAlgorithmException, InvalidKeyException, IOException;
    public Order[] getOpenOrders() throws NoSuchAlgorithmException, InvalidKeyException, CloneNotSupportedException, IOException;
    public Order getOrder(String order_id) throws CloneNotSupportedException, NoSuchAlgorithmException, InvalidKeyException, IOException;
    public Product[] getProducts() throws IOException;
    public Fill[] getFills();
    public String getMarketDataOrderBook(String product, String level) throws IOException;

    //ProductOrderBook getMarketDataOrderBookProduct(String product, String level) throws IOException;
}
