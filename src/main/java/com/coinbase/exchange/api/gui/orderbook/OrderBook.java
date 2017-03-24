package com.coinbase.exchange.api.gui.orderbook;

import com.coinbase.exchange.api.gui.custom.MyTableModel;
import com.coinbase.exchange.api.marketdata.MarketData;
import com.coinbase.exchange.api.marketdata.MarketDataService;
import com.coinbase.exchange.api.marketdata.OrderItem;
import com.coinbase.exchange.api.websocketfeed.WebsocketFeed;
import com.coinbase.exchange.api.websocketfeed.message.HeartBeat;
import com.coinbase.exchange.api.websocketfeed.message.OrderBookMessage;
import com.coinbase.exchange.api.websocketfeed.message.OrderReceivedOrderBookMessage;
import com.coinbase.exchange.api.websocketfeed.message.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;

/**
 * Created by robevansuk on 10/03/2017.
 */
@Component
public class OrderBook extends JPanel {

    static final Logger log = LoggerFactory.getLogger(OrderBook.class);
    static String[] productIds = new String[]{"BTC-GBP"}; // make this configurable.

    WebsocketFeed websocketFeed;
    MarketDataService marketDataService;

    private boolean isAlive;
    Map<String, JSplitPane> orderBookPane;

    Map<String, JTable> bidTables;
    Map<String, JTable> askTables;

    Map<String, List<OrderItem>> bids;
    Map<String, List<OrderItem>> asks;

    Map<String, JScrollPane> bidScrollPanes;
    Map<String, JScrollPane> askScrollPanes;

    @Autowired
    public OrderBook(MarketDataService marketDataService, WebsocketFeed websocketFeed) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.websocketFeed = websocketFeed;
        this.marketDataService = marketDataService;
        this.bidTables = new HashMap<>();
        this.askTables = new HashMap<>();
        this.orderBookPane = new HashMap<>();
        this.bids = new HashMap<>();
        this.asks = new HashMap<>();
        this.bidScrollPanes = new HashMap<>();
        this.askScrollPanes = new HashMap<>();
        this.setVisible(true);
        SwingUtilities.invokeLater(() -> load());
    }

    public void load() {
        OrderBook thisOrderBook = this;
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() {

                log.info("*********** OrderBook.load()");
                for (String productId : productIds) {

                    log.info("******** Getting the orderbook for: " + productId);
                    MarketData marketData = marketDataService.getMarketDataOrderBook(productId, "2");

                    bids.put(productId, new LinkedList<>(marketData.getBids()));
                    asks.put(productId, new LinkedList<>(marketData.getAsks()));

                    JPanel panel = new JPanel();
                    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                    log.info("Populating ASK table for: " + productId);
                    JTable askTable = initTable(asks.get(productId));
                    askTables.put(productId, askTable);
                    log.info("Populating BID table for: " + productId);
                    JTable bidTable = initTable(bids.get(productId));
                    bidTables.put(productId, bidTable);

                    JScrollPane scrollerAsks = new JScrollPane(askTable);
                    JScrollPane scrollerBids = new JScrollPane(bidTable);

                    askScrollPanes.put(productId, scrollerAsks);
                    bidScrollPanes.put(productId, scrollerBids);

                    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollerAsks, scrollerBids);



                    orderBookPane.put(productId, splitPane);

                }
                JSplitPane splitPane = orderBookPane.get(productIds[0]);
                add(splitPane);

                splitPane.setDividerLocation(splitPane.getParent().getHeight()/2);
                log.info("******** Opening Websocket Feed");
                return null;
            }

            @Override
            public void done() {

            }
        };
        worker.execute();
        websocketFeed.subscribe(new Subscribe(productIds), thisOrderBook);

    }

    private JTable initTable(List<OrderItem> marketData) {
        MyTableModel model = new MyTableModel();

        int index = 0;
        for (OrderItem item : marketData) {
            log.info("Inserting new OrderItem row at index: " + index);
            model.insertRowAt(item, index);
            index++;
        }

        return new JTable(model);
    }

    public void heartBeat(HeartBeat object) {
        isAlive = true;
    }

    /**
     * TODO this needs refactoring
     *
     * @param msg
     */
    public void updateOrderBookReceived(OrderReceivedOrderBookMessage msg) {
        log.info("OrderBook.updateOrderBookReceived");
        JScrollBar bidVerticalScrollBar = bidScrollPanes.get(msg.getProduct_id()).getVerticalScrollBar();
        JScrollBar askVerticalScrollBar = askScrollPanes.get(msg.getProduct_id()).getVerticalScrollBar();

        boolean lockBidToMin = false;
        boolean lockAskToMax = false;

        if (bidVerticalScrollBar.getValue() <= bidVerticalScrollBar.getMinimum()) {
            lockBidToMin = true;
        }
        log.info("Ask Scroll bar value: "+ askVerticalScrollBar.getValue()+ ", Maximum: " + askVerticalScrollBar.getMaximum());
        if (askVerticalScrollBar.getValue()+200 >= askVerticalScrollBar.getMaximum()) {
            lockAskToMax = true;
        }

        insertOrderIntoTable(msg);

        if (lockBidToMin) {
            bidVerticalScrollBar.setValue(bidVerticalScrollBar.getMinimum());
        }
        if (lockAskToMax) {
            askVerticalScrollBar.setValue(askVerticalScrollBar.getMaximum());
        }
    }

    private void insertOrderIntoTable(OrderReceivedOrderBookMessage msg) {
        MyTableModel model;
        boolean notUpdated = false;
        if (msg.getSide().equals("buy")) { // BUYS / BIDS

            model = (MyTableModel) bidTables.get(msg.getProduct_id()).getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
//                log.info("Checking BID price for row: " + i);
                BigDecimal modelPrice = getPrice((String) model.getValueAt(i, 0));
                BigDecimal incomingPrice = getPrice(msg);

                if (incomingPrice.compareTo(modelPrice) == 0) {
                    log.info("Updating BID price: " + getPrice(msg) + ", row:"+ i + ", with size: " + getOrderSize(msg));
                    model.updateExistingRow(msg, i);
                    notUpdated = false;
                    break;
                } else if (incomingPrice.compareTo(modelPrice) == 1) {
                    log.info("Inserting new BID price: " + getPrice(msg) + ", row:"+ i);
                    model.insertRowAt(msg, i);
                    notUpdated = false;
                    break;
                }
            }
            if (notUpdated) {
                // append to the end of the model
                model.appendTop(msg);
            }
        } else { // ASKS / SELLS
            model = (MyTableModel) askTables.get(msg.getProduct_id()).getModel();

            for (int i = 0; i < model.getRowCount(); i++) {
//                log.info("Checking ASK price for row: " + i);
                BigDecimal modelPrice = getPrice((String) model.getValueAt(i, 0));
                BigDecimal incomingPrice = getPrice(msg);

                if (incomingPrice.compareTo(modelPrice) == 0) {
                    log.info("Updating ASK price: " + i);
                    model.updateExistingRow(msg, i);
                    notUpdated = false;
                    break;
                } else if (incomingPrice.compareTo(modelPrice) == -1) {
                    log.info("Inserting new ASK price: " + i);
                    model.insertRowAt(msg, i);
                    notUpdated = false;
                    break;
                }
            }
            if (notUpdated) {
                log.info("Appending ASK to bottom of list. Price: " + getPrice(msg));
                // append to the end of the model
                model.appendBottom(msg);
            }
        }
    }

    private BigDecimal getPrice(OrderBookMessage msg) {
        return msg.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    private BigDecimal getPrice(String priceString) {
        return new BigDecimal(priceString).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    private BigDecimal getOrderSize(OrderBookMessage msg) {
        return msg.getPrice().setScale(5, BigDecimal.ROUND_HALF_UP);
    }

    public void updateOrderBook(OrderReceivedOrderBookMessage order) {

        SwingWorker worker = new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() {
                SwingUtilities.invokeLater(() -> {
                    updateOrderBookReceived(order);
                });
                return null;
            }

            @Override
            public void done() {
                log.info("OrderBook.done()");
                updateUI();
            }
        };

        worker.execute();
    }
}
