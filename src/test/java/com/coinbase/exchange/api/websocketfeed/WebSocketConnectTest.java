package com.coinbase.exchange.api.websocketfeed;

import com.coinbase.exchange.api.BaseTest;
import com.coinbase.exchange.api.websocketfeed.message.Subscribe;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class WebSocketConnectTest extends BaseTest {

    @Autowired
    WebsocketFeed websocketFeed;

    @Test
    public void canConnect() throws InterruptedException {
        String[] products = {"BTC-USD"};
        websocketFeed.subscribe(new Subscribe(products));
    }
}
