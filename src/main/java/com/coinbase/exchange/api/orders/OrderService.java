package com.coinbase.exchange.api.orders;

import com.coinbase.exchange.api.entity.Fill;
import com.coinbase.exchange.api.entity.Hold;
import com.coinbase.exchange.api.entity.NewOrderSingle;
import com.coinbase.exchange.api.exchange.GdaxExchange;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

/**
 * Created by robevansuk on 03/02/2017.
 */

public class OrderService {


    GdaxExchange exchange;

    public static final String ORDERS_ENDPOINT = "/orders";

    public List<Hold> getHolds(String accountId) {
        return exchange.getAsList(ORDERS_ENDPOINT + "/" + accountId + "/holds", Hold[].class);
    }

    public List<Order> getOpenOrders(String accountId) {
        return exchange.getAsList(ORDERS_ENDPOINT + "/" + accountId + "/orders", Order[].class);
    }

    public Order getOrder(String orderId) {
        return exchange.get(ORDERS_ENDPOINT + "/" + orderId, Order.class);
    }

    public Order createOrder(NewOrderSingle order) {
        Gson gson = new Gson();
        return exchange.post(ORDERS_ENDPOINT, Order.class, gson.toJson(order));
    }

    public String cancelOrder(String orderId) {
        String deleteEndpoint = ORDERS_ENDPOINT + "/" + orderId;
        return exchange.delete(deleteEndpoint, String.class);
    }

    public List<Order> getOpenOrders() {
        return exchange.getAsList(ORDERS_ENDPOINT, Order[].class);
    }

    public List<Order> cancelAllOpenOrders() {
        return Arrays.asList(exchange.delete(ORDERS_ENDPOINT, Order[].class));
    }

    public List<Fill> getAllFills() {
        String fillsEndpoint = "/fills";
        return exchange.getAsList(fillsEndpoint, Fill[].class);
    }
}


