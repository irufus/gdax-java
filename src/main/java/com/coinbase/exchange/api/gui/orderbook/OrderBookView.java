package com.coinbase.exchange.api.gui.orderbook;

import com.coinbase.exchange.api.marketdata.MarketData;
import com.coinbase.exchange.api.marketdata.MarketDataService;
import com.coinbase.exchange.api.orders.Order;
import com.coinbase.exchange.api.websocketfeed.WebsocketfeedService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;

/**
 * Created by robevansuk on 10/03/2017.
 */
public class OrderBookView extends JPanel {

    @Autowired
    MarketDataService marketDataService;

    @Autowired
    WebsocketfeedService websocketFeed;

    JList<String> bids = new JList<>();
    JList<String> sells = new JList<>();

    public OrderBookView(){
        super();
        String[] productIds = new String[] { "BTC-GBP", "BTC-ETH" }; // make this configurable.
        for (String productId : productIds) {
            MarketData marketData = marketDataService.getMarketDataOrderBook(productId, "1");
            initBids();
            initAsks();
        }

        // start live feed(s)
        websocketFeed.openWebsocketFeed(productIds);
    }

    private void initAsks() {
        // init the asks JList
    }


    private void initBids() {
        // init the bids JList
    }

    public void newTrade(String msg) {
        // do something with the new trade
    }
}
