package com.coinbase.exchange.api.websocketfeed;

import com.coinbase.exchange.api.exchange.GdaxExchange;
import com.coinbase.exchange.api.orders.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

/**
 * Created by robevansuk on 12/03/2017.
 */
@Component
public class WebsocketfeedService {

    @Autowired
    GdaxExchange gdaxExchange;

    public void openWebsocketFeed(String[] productIds){
        gdaxExchange.websocketFeed(new SubscribeMessage(productIds));
    }
}
