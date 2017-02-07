package com.coinbase.exchange.api.orders;

import com.coinbase.exchange.api.entity.Fill;
import com.coinbase.exchange.api.entity.Hold;
import com.coinbase.exchange.api.entity.NewOrderSingle;
import com.coinbase.exchange.api.exchange.CoinbaseExchange;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    RestTemplate restTemplate;

    public static final String ORDERS_ENDPOINT = "/orders";

    public Hold[] getHolds(String accountId) throws CloneNotSupportedException, InvalidKeyException {
        ResponseEntity<Hold[]> response = restTemplate.exchange(ORDERS_ENDPOINT + "/" + accountId + "/holds",
                HttpMethod.GET,
                exchange.securityHeaders(ORDERS_ENDPOINT + "/" + accountId + "/holds", "GET", ""),
                Hold[].class);
        return response.getBody();
    }

    public Order[] getOpenOrders(String accountId) throws CloneNotSupportedException, InvalidKeyException {
        ResponseEntity<Order[]> response = restTemplate.exchange(
                ORDERS_ENDPOINT + "/" + accountId + "/orders",
                HttpMethod.GET,
                exchange.securityHeaders(ORDERS_ENDPOINT + "/" + accountId + "/holds", "GET", ""),
                Order[].class);
        return response.getBody();
    }

    public Order getOrder(String orderId) throws CloneNotSupportedException, InvalidKeyException {
        ResponseEntity<Order> response = restTemplate.exchange(ORDERS_ENDPOINT + "/" + orderId,
                HttpMethod.GET,
                exchange.securityHeaders(ORDERS_ENDPOINT + "/" + orderId, "GET", ""),
                Order.class);
        return response.getBody();
    }

    public Order createOrder(NewOrderSingle order) throws CloneNotSupportedException, InvalidKeyException {
        String createOrderEndpoint = ORDERS_ENDPOINT + "/" + order;
        ResponseEntity<Order> response = restTemplate.exchange(createOrderEndpoint,
                HttpMethod.POST,
                exchange.securityHeaders(createOrderEndpoint, "POST", order.toString()),
                Order.class);
        return response.getBody();
    }

    public String cancelOrder(String orderId) {
        String result = null;
        try {
            result = executeDeleteRequest(orderId);
        } catch (NoSuchAlgorithmException | InvalidKeyException | CloneNotSupportedException | IOException e){

        }
        return result;
    }

    public Order[] getOpenOrders() throws CloneNotSupportedException, InvalidKeyException {
        String ordersEndpoint = exchange.getBaseUrl() + "/orders";
        ResponseEntity<Order[]> orders = restTemplate.exchange(ordersEndpoint,
                HttpMethod.GET,
                exchange.securityHeaders(ordersEndpoint, "GET", ""),
                Order[].class);
        return orders.getBody();
    }

    public String executeDeleteRequest(String orderId) throws NoSuchAlgorithmException, InvalidKeyException, CloneNotSupportedException, IOException {
        String deleteEndpoint = ORDERS_ENDPOINT + "/" + orderId;
        ResponseEntity<String> response = restTemplate.exchange(deleteEndpoint,
                HttpMethod.DELETE,
                exchange.securityHeaders(deleteEndpoint, "DELETE", ""),
                String.class);
        return response.getBody();
    }

    public Fill[] getAllFills() throws CloneNotSupportedException, InvalidKeyException {
        String fillsEndpoint ="/fills";
        ResponseEntity<Fill[]> response = restTemplate.exchange(fillsEndpoint,
                HttpMethod.GET,
                exchange.securityHeaders(fillsEndpoint, "GET", ""),
                Fill[].class);
        return response.getBody();
    }
}


