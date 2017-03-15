package com.coinbase.exchange.api.gui.orderbook;

import com.coinbase.exchange.api.marketdata.MarketData;
import com.coinbase.exchange.api.marketdata.MarketDataService;
import com.coinbase.exchange.api.websocketfeed.WebsocketfeedService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by robevansuk on 10/03/2017.
 */
public class OrderBookView extends JPanel {

    @Autowired
    MarketDataService marketDataService;

    @Autowired
    WebsocketfeedService websocketFeed;

    Map<String, JList<BigDecimal>> orderBook = new HashMap<>();

    public OrderBookView(){
        super();
        String[] productIds = new String[] { "BTC-GBP", "BTC-ETH" }; // make this configurable.
        for (String productId : productIds) {
            MarketData marketData = marketDataService.getMarketDataOrderBook(productId, "2");
            orderBook.put(productId+"_bids", initJList(marketData.getBids()));
            orderBook.put(productId+"_asks", initJList(marketData.getAsks()));
        }

        // start live feed(s)
        websocketFeed.openWebsocketFeed(productIds);
    }

    private JList<BigDecimal> initJList(BigDecimal[][] marketData) {
        DefaultListModel<BigDecimal> model = new DefaultListModel();
        for (BigDecimal[] arr : marketData) {
            model.addElement(arr[0]);
        }
        return new JList(model);
    }

    public void newTrade(String msg) {
        Gson gson = new Gson();
        Message message = gson.fromJson(msg);
    }

    public void updateThisPanel(){

    }
}
