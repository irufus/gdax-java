package com.coinbase.exchange.api.orders;

import com.coinbase.exchange.api.entity.Fill;
import com.coinbase.exchange.api.entity.Hold;
import com.coinbase.exchange.api.entity.NewOrderSingle;
import com.coinbase.exchange.api.exchange.GdaxExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by robevansuk on 03/02/2017.
 */
@Component
public class OrderService {

    @Autowired
    GdaxExchange exchange;

    public static final String ORDERS_ENDPOINT = "/orders";
    public static final String FILLS_ENDPOINT = "/fills";

    public List<Hold> getHolds(String accountId) {
        return exchange.getAsList(ORDERS_ENDPOINT + "/" + accountId + "/holds", new ParameterizedTypeReference<Hold[]>(){});
    }

    public List<Order> getOpenOrders(String accountId) {
        return exchange.getAsList(ORDERS_ENDPOINT + "/" + accountId + "/orders", new ParameterizedTypeReference<Order[]>(){});
    }

    public Order getOrder(String orderId) {
        return exchange.get(ORDERS_ENDPOINT + "/" + orderId,new ParameterizedTypeReference<Order>(){});
    }

    public Order createOrder(NewOrderSingle order) {
        return exchange.post(ORDERS_ENDPOINT, new ParameterizedTypeReference<Order>(){}, order);
    }

    public String cancelOrder(String orderId) {
        String deleteEndpoint = ORDERS_ENDPOINT + "/" + orderId;
        return exchange.delete(deleteEndpoint, new ParameterizedTypeReference<String>(){});
    }

    public List<Order> getOpenOrders() {
        return exchange.getAsList(ORDERS_ENDPOINT, new ParameterizedTypeReference<Order[]>(){});
    }

    public List<Order> cancelAllOpenOrders() {
        return Arrays.asList(exchange.delete(ORDERS_ENDPOINT, new ParameterizedTypeReference<Order[]>(){}));
    }

    public List<Fill> getFillsByProductId(String product_id, int resultLimit) {
        return exchange.getAsList(FILLS_ENDPOINT + "?product_id=" + product_id + "&limit=" + resultLimit, new ParameterizedTypeReference<Fill[]>(){});
    }
    
    public List<Fill> getFillByOrderId(String order_id, int resultLimit) {
        return exchange.getAsList(FILLS_ENDPOINT + "?order_id=" + order_id + "&limit=" + resultLimit, new ParameterizedTypeReference<Fill[]>(){});
    }
}


