package com.coinbase.exchange.api.gui.orderbook;

import com.coinbase.exchange.api.marketdata.MarketData;
import com.coinbase.exchange.api.marketdata.MarketDataService;
import com.coinbase.exchange.api.websocketfeed.WebsocketfeedService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Created by robevansuk on 10/03/2017.
 */
public class OrderBookView extends JPanel {

    @Autowired
    MarketDataService marketDataService;

    @Autowired
    WebsocketfeedService websocketFeed;

    JList<String> bids = new JList<>();
    JList<String> asks = new JList<>();

    public OrderBookView(){
        super();
        String[] productIds = new String[] { "BTC-GBP", "BTC-ETH" }; // make this configurable.
        for (String productId : productIds) {
            MarketData marketData = marketDataService.getMarketDataOrderBook(productId, "2");
            this.add(initJList(bids, marketData));
            this.add(initJList(asks, marketData));
        }

        // start live feed(s)
        websocketFeed.openWebsocketFeed(productIds);
    }

    private JList initJList(JList list, MarketData marketData) {
        // init the asks JList
        BigDecimal[] priceList = Arrays.stream(marketData.getAsks()));
        ListModel model = new DefaultComboBoxModel(priceList);
        list.setModel(model);
    }

    public void newTrade(String msg) {
        // do something with the new trade
    }
}
