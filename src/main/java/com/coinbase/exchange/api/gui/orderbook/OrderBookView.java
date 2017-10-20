package com.coinbase.exchange.api.gui.orderbook;

import com.coinbase.exchange.api.gui.custom.OrderBookModel;
import com.coinbase.exchange.api.marketdata.MarketData;
import com.coinbase.exchange.api.marketdata.MarketDataService;
import com.coinbase.exchange.api.marketdata.OrderItem;
import com.coinbase.exchange.api.websocketfeed.WebsocketFeed;
import com.coinbase.exchange.api.websocketfeed.message.HeartBeat;
import com.coinbase.exchange.api.websocketfeed.message.OrderBookMessage;
import com.coinbase.exchange.api.websocketfeed.message.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.List;

/**
 * TODO - convert to JFX rather than using Swing.
 * <p>
 * Created by robevansuk on 10/03/2017.
 */
@Component
public class OrderBookView extends JPanel {

    static final Logger log = LoggerFactory.getLogger(OrderBookView.class);
    static String[] productIds = new String[]{"BTC-GBP", "ETH-BTC"}; // make this configurable.

    private boolean isAlive;

    WebsocketFeed websocketFeed;
    MarketDataService marketDataService;

    Map<String, JPanel> orderBookSplitPaneMap;
    Map<String, JTable> tables;

    Map<String, List<OrderItem>> bids;
    Map<String, List<OrderItem>> asks;

    Map<String, JScrollPane> scrollPanes;

    // limit order book viewer
    JPanel lobViewer;
    JPanel productSelectionPanel;
    Map<String, Long> maxSequenceIds;

    @Autowired
    public OrderBookView(@Value("${gui.enabled}") boolean guiEnabled, MarketDataService marketDataService, WebsocketFeed websocketFeed) {
        super();
        if (guiEnabled) {
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            this.lobViewer = new JPanel();
            this.add(lobViewer);

            this.productSelectionPanel = new JPanel();
            this.productSelectionPanel.setLayout(new BoxLayout(productSelectionPanel, BoxLayout.X_AXIS));
            this.add(productSelectionPanel);

            this.websocketFeed = websocketFeed;
            this.marketDataService = marketDataService;

            this.orderBookSplitPaneMap = new HashMap<>();
            this.maxSequenceIds = new HashMap<>();
            this.tables = new HashMap<>();
            this.bids = new HashMap<>();
            this.asks = new HashMap<>();
            this.scrollPanes = new HashMap<>();
            this.setVisible(true);

            try {
                SwingUtilities.invokeAndWait(() -> load());
            } catch (InterruptedException | InvocationTargetException e) {
                log.error("Something went wrong whilst starting the OrderBookView", e);
            }
        }
    }

    /**
     * Gets the market data and loads in all the prices for the current order book,
     * then submits a subscribe message to the server so that price updates are received.
     */
    public void load() {
        OrderBookView thisOrderBook = this;
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() {

                log.info("*********** OrderBookView.load()");
                for (String productId : productIds) {

                    log.info("******** Getting the orderbook for: " + productId);
                    MarketData marketData = marketDataService.getMarketDataOrderBook(productId, "2");

                    Collections.sort(marketData.getAsks());

                    bids.put(productId, new LinkedList<>(marketData.getBids())); // list of order book items
                    asks.put(productId, new LinkedList<>(marketData.getAsks())); // list of order book items

                    log.info("Populating ASK table for: " + productId);
                    JTable askTable = initTable(asks.get(productId));
                    tables.put("sell_" + productId, askTable);

                    log.info("Populating BID table for: " + productId);
                    JTable bidTable = initTable(bids.get(productId));
                    tables.put("buy_" + productId, bidTable);

                    JScrollPane scrollerAsks = new JScrollPane(askTable);
                    JScrollPane scrollerBids = new JScrollPane(bidTable);

                    scrollPanes.put("sell_" + productId, scrollerAsks);
                    scrollPanes.put("buy_" + productId, scrollerBids);

                    JPanel orderBookPanelView = new JPanel();
                    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollerAsks, scrollerBids);
                    int height = thisOrderBook.getHeight() / 2;
                    splitPane.setDividerLocation(height);
                    orderBookPanelView.add(splitPane);

                    orderBookSplitPaneMap.put(productId, orderBookPanelView);
                    maxSequenceIds.put(productId, new Long(0));
                }

                setLobViewer(productIds[0]);
                log.info("******** Opening Websocket Feed");

                for (String productId : productIds) {
                    JButton button = new JButton(productId);
                    button.setFont(new Font("Calibri", Font.PLAIN, 11));
                    productSelectionPanel.add(button);
                    button.addActionListener(event -> setLobViewer(event.getActionCommand()));
                }

                log.info("*** Subscribing ***");
                websocketFeed.subscribe(new Subscribe(productIds), thisOrderBook);
                return null;
            }

            @Override
            public void done() {
                repaint();
                updateUI();
            }
        };
        worker.execute();
    }

    private JTable initTable(List<OrderItem> marketData) {
        OrderBookModel model = new OrderBookModel();

        int index = 0;
        for (OrderItem item : marketData) {
            log.info("Inserting new OrderItem row at index: {}, {}, {}", item.getPrice(), item.getSize(), item.getNum());
            model.insertRowAt(item, index);
            index++;
        }

        JTable table = new JTable(model);
        table.setFont(new Font("Calibri", Font.PLAIN, 10));
        return table;
    }

    public void heartBeat(HeartBeat object) {
        isAlive = true;
    }

    /**
     * @param msg
     */
    public void updateOB(OrderBookMessage msg) {
        log.info("OrderBookView.updateOrderBook");
        OrderBookModel model = ((OrderBookModel) tables.get(msg.getSide() + "_" + msg.getProduct_id()).getModel());

        model.incomingOrder(msg);

        JScrollBar bidVerticalScrollBar = scrollPanes.get("buy_" + msg.getProduct_id()).getVerticalScrollBar();
        JScrollBar askVerticalScrollBar = scrollPanes.get("sell_" + msg.getProduct_id()).getVerticalScrollBar();
        askVerticalScrollBar.setValue(askVerticalScrollBar.getMaximum());
        bidVerticalScrollBar.setValue(bidVerticalScrollBar.getMinimum());
    }

    public void updateOrderBook(OrderBookMessage order) {

        SwingWorker worker = new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() {
                SwingUtilities.invokeLater(() -> updateOB(order));
                return null;
            }

            @Override
            public void done() {
                repaint();
                updateUI();
            }
        };

        worker.execute();
    }

    public void setLobViewer(String productId) {
        JPanel splitPane = orderBookSplitPaneMap.get(productId);
        lobViewer.removeAll();
        lobViewer.add(splitPane);
        repaint();
        updateUI();
    }


}
