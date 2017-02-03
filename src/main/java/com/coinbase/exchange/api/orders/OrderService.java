package com.coinbase.exchange.api.orders;

import com.coinbase.exchange.api.authentication.Authentication;
import com.coinbase.exchange.api.entity.NewOrderSingle;
import com.coinbase.exchange.api.exchange.CoinbaseExchange;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by robevansuk on 03/02/2017.
 */
@Component
public class OrderService {

    @Autowired
    CoinbaseExchange exchange;

    @Autowired
    Authentication auth;

    public Order createOrder(NewOrderSingle order) throws CloneNotSupportedException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        Gson gson = new Gson();
        String body = gson.toJson(order);
        String json = exchange.generatePostRequestJSON("/orders", body);
        return gson.fromJson(json, Order.class);
    }

    public String cancelOrder(String orderid) throws NoSuchAlgorithmException, InvalidKeyException, CloneNotSupportedException, IOException {
        return exchange.executeDeleteRequest(orderid);
    }

    public Order[] getOpenOrders() throws NoSuchAlgorithmException, InvalidKeyException, CloneNotSupportedException, IOException {
        String endpoint = "/orders";
        String json = exchange.generateGetRequestJSON(endpoint);
        Gson gson = new Gson();
        Order[] orders = gson.fromJson(json, Order[].class);
        return orders;
    }

}


