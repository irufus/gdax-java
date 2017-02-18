package com.coinbase.exchange.api.orders;

import com.coinbase.exchange.api.entity.Fill;
import com.coinbase.exchange.api.entity.Hold;
import com.coinbase.exchange.api.entity.NewOrderSingle;
import com.coinbase.exchange.api.exchange.GdaxExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

/**
 * Created by robevansuk on 03/02/2017.
 */
@Component
public class OrderService {

    @Autowired
    GdaxExchange exchange;

    public static final String ORDERS_ENDPOINT = "/orders";

    public Hold[] getHolds(String accountId) {
        return exchange.get(ORDERS_ENDPOINT + "/" + accountId + "/holds",
                new ParameterizedTypeReference<Hold[]>(){});
    }

    public Hold[] getHolds(String accountId,
                              String beforeOrAfter,
                              Integer pageNumber,
                              Integer limit) {
        return exchange.pagedGet(ORDERS_ENDPOINT + "/" + accountId + "/holds",
                new ParameterizedTypeReference<Hold[]>(){},
                beforeOrAfter,
                pageNumber,
                limit);
    }

    public Order[] getOpenOrders(String accountId) {
        return exchange.get(ORDERS_ENDPOINT + "/" + accountId + "/orders",
                new ParameterizedTypeReference<Order[]>(){});
    }

    public Order[] getPagedOpenOrders(String accountId,
                                      String beforeOrAfter,
                                      Integer pageNumber,
                                      Integer limit) {
        return exchange.pagedGet(ORDERS_ENDPOINT + "/" + accountId + "/orders",
                new ParameterizedTypeReference<Order[]>(){},
                beforeOrAfter,
                pageNumber,
                limit);
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

    public Order[] getOpenOrders() {
        return exchange.get(ORDERS_ENDPOINT, new ParameterizedTypeReference<Order[]>(){});
    }

    public Order[] getPagedOpenOrders(String beforeOrAfter,
                                 Integer pageNumber,
                                 Integer limit) {
        return exchange.pagedGet(ORDERS_ENDPOINT, new ParameterizedTypeReference<Order[]>(){},
                beforeOrAfter,
                pageNumber,
                limit);
    }


    /**
     * haven't paged this - seems unnecessary?
     * @return
     */
    public Order[] cancelAllOpenOrders() {
        return exchange.delete(ORDERS_ENDPOINT, new ParameterizedTypeReference<Order[]>(){});
    }

    public Fill[] getAllFills() {
        String fillsEndpoint = "/fills";
        return exchange.get(fillsEndpoint, new ParameterizedTypeReference<Fill[]>(){});
    }

    public Fill[] getPagedFills(String beforeOrAfter,
                                Integer pageNumber,
                                Integer limit) {
        String fillsEndpoint = "/fills";
        return exchange.pagedGet(fillsEndpoint, new ParameterizedTypeReference<Fill[]>(){},
                beforeOrAfter,
                pageNumber,
                limit);
    }
}


