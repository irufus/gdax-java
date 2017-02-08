package com.coinbase.exchange.api.orders;

import com.coinbase.exchange.api.entity.Fill;
import com.coinbase.exchange.api.entity.Hold;
import com.coinbase.exchange.api.entity.NewOrderSingle;
import com.coinbase.exchange.api.exchange.CoinbaseExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.security.InvalidKeyException;

/**
 * Created by robevansuk on 03/02/2017.
 */
@Component
public class OrderService {

    @Autowired
    CoinbaseExchange exchange;

    public static final String ORDERS_ENDPOINT = "/orders";

    public Hold[] getHolds(String accountId) {
        return exchange.get(ORDERS_ENDPOINT + "/" + accountId + "/holds", new ParameterizedTypeReference<Hold[]>(){});
    }

    public Order[] getOpenOrders(String accountId) {
        return exchange.get(ORDERS_ENDPOINT + "/" + accountId + "/orders", new ParameterizedTypeReference<Order[]>(){});
    }

    public Order getOrder(String orderId) {
        return exchange.get(ORDERS_ENDPOINT + "/" + orderId,new ParameterizedTypeReference<Order>(){});
    }

    public Order createOrder(NewOrderSingle order) {
        String createOrderEndpoint = ORDERS_ENDPOINT + "/" + order;
        return exchange.post(createOrderEndpoint, new ParameterizedTypeReference<Order>(){}, order.toString());
    }

    public String cancelOrder(String orderId) {
        return executeDeleteRequest(orderId);
    }

    public Order[] getOpenOrders() {
        return exchange.get(ORDERS_ENDPOINT, new ParameterizedTypeReference<Order[]>(){});
    }

    public String executeDeleteRequest(String orderId) {
        String deleteEndpoint = ORDERS_ENDPOINT + "/" + orderId;
        return exchange.delete(deleteEndpoint, new ParameterizedTypeReference<String>(){});
    }

    public Fill[] getAllFills() throws CloneNotSupportedException, InvalidKeyException {
        String fillsEndpoint = "/fills";
        return exchange.get(fillsEndpoint, new ParameterizedTypeReference<Fill[]>(){});
    }
}


