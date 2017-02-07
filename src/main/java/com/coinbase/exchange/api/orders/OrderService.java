package com.coinbase.exchange.api.orders;

import com.coinbase.exchange.api.entity.Fill;
import com.coinbase.exchange.api.entity.Hold;
import com.coinbase.exchange.api.entity.NewOrderSingle;
import com.coinbase.exchange.api.exchange.CoinbaseExchange;
import com.coinbase.exchange.api.exchange.GenericParameterizedType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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

    public static final String ORDERS_ENDPOINT = "/orders";

    public Hold[] getHolds(String accountId) {
        return exchange.get(ORDERS_ENDPOINT + "/" + accountId + "/holds", new GenericParameterizedType<Hold[]>());
    }

    public Order[] getOpenOrders(String accountId) {
        return exchange.get(ORDERS_ENDPOINT + "/" + accountId + "/orders", new GenericParameterizedType<Order[]>());
    }

    public Order getOrder(String orderId) {
        return exchange.get(ORDERS_ENDPOINT + "/" + orderId,new GenericParameterizedType<Order>());
    }

    public Order createOrder(NewOrderSingle order) {
        String createOrderEndpoint = ORDERS_ENDPOINT + "/" + order;
        return exchange.post(createOrderEndpoint, new GenericParameterizedType<Order>(), order.toString());
    }

    public String cancelOrder(String orderId) {
        return executeDeleteRequest(orderId);
    }

    public Order[] getOpenOrders() {
        return exchange.get(ORDERS_ENDPOINT, new GenericParameterizedType<Order[]>());
    }

    public String executeDeleteRequest(String orderId) {
        String deleteEndpoint = ORDERS_ENDPOINT + "/" + orderId;
        return exchange.delete(deleteEndpoint, new GenericParameterizedType<String>());
    }

    public Fill[] getAllFills() throws CloneNotSupportedException, InvalidKeyException {
        String fillsEndpoint = "/fills";
        return exchange.get(fillsEndpoint, new GenericParameterizedType<Fill[]>());
    }
}


