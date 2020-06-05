package com.coinbase.exchange.gui.liveorderbook.view;

import com.coinbase.exchange.api.marketdata.MarketData;
import com.coinbase.exchange.api.marketdata.MarketDataService;
import com.coinbase.exchange.api.marketdata.OrderItem;
import com.coinbase.exchange.gui.liveorderbook.OrderBookModel;
import com.coinbase.exchange.gui.websocketfeed.WebsocketFeed;
import com.coinbase.exchange.gui.websocketfeed.WebsocketMessageHandler;
import com.coinbase.exchange.websocketfeed.HeartBeat;
import com.coinbase.exchange.websocketfeed.OrderBookMessage;
import com.coinbase.exchange.websocketfeed.Subscribe;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static javax.swing.JSplitPane.VERTICAL_SPLIT;

/**
 * TODO - convert to JFX rather than using Swing.
 * <p>
 * Created by robevansuk on 10/03/2017.
 */
public class OrderBookView extends JPanel {

    private static final Logger log = LoggerFactory.getLogger(OrderBookView.class);
    private static final int FULL_ORDER_BOOK = 3;

    private String productId;

    private boolean isWebsocketAlive;
    private boolean isOrderbookReady;

    private ObjectMapper objectMapper;
    private WebsocketFeed websocketFeed;
    private MarketDataService marketDataService;

    private Map<String, JTable> bidAskTables;
    private Map<String, JScrollPane> scrollPanes;

    private List<OrderItem> bids;
    private List<OrderItem> asks;

    // limit order book viewer
    private JPanel rootPanel;
    private JPanel productSelectionPanel;
    private Long maxSequenceId;
    private boolean guiEnabled;
    private OrderBookView orderBook;
    private SwingWorker<Void, Void> websocketFeedStarter;


    public OrderBookView(boolean guiEnabled,
                         String currentProductSelected,
                         MarketDataService marketDataService,
                         WebsocketFeed websocketFeed,
                         ObjectMapper objectMapper) {
        super();
        if (guiEnabled) {
            this.guiEnabled = guiEnabled;
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.productId = currentProductSelected;
            this.orderBook = this;
            this.objectMapper = objectMapper;
            this.rootPanel = new JPanel();
            this.add(rootPanel);

            // TODO this should be a drop down list on a menu bar rather than buttons
            this.productSelectionPanel = new JPanel();
            this.productSelectionPanel.setLayout(new BoxLayout(productSelectionPanel, BoxLayout.X_AXIS));
            this.add(productSelectionPanel);

            this.websocketFeed = websocketFeed;
            this.marketDataService = marketDataService;

            this.bidAskTables = new HashMap<>();
            this.scrollPanes = new HashMap<>();
            this.bids = new ArrayList<>();
            this.asks = new ArrayList<>();
            this.setVisible(true);

            try {
                log.info("*********** OrderBookView.load()");
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
        if (guiEnabled) {
            isOrderbookReady = false;
            websocketFeedStarter = new SwingWorker<>() {
                @Override
                public Void doInBackground() throws InterruptedException {
                    // Queue up websocketFeed messages before getting market data
                    openWebsocket(orderBook);

                    log.info("******** Initialising view for: {}", productId);
                    MarketData marketData = getMarketData();
                    setMaxSequenceId(marketData.getSequence());

                    JScrollPane scrollableAsks = addToScrollPane("sell", getAskTable());
                    JScrollPane scrollableBids = addToScrollPane("buy", getBidTable());

                    JPanel orderBookPanelView = new JPanel();
                    JSplitPane splitScrollableTables = getSplitScrollableTables(scrollableAsks, scrollableBids);
                    orderBookPanelView.add(splitScrollableTables);

                    /**
                     * This adds the two tables in scrollPanes within the split view
                     */
                    setLimitOrderBookViewer(orderBookPanelView);
                    log.info("******** Activate Orderbook");
                    log.info("Market Data Sequence: {}, Product: {}", maxSequenceId, productId);
                    isOrderbookReady = true;
                    Long marketDataSequenceId = marketData.getSequence();
                    List<OrderBookMessage> unappliedMessages = websocketFeed.getOrdersAfter(marketDataSequenceId);
                    unappliedMessages.stream().forEach(msg -> {
                        log.info("Applying unapplied messages after {}: sequenceId: {}, price: {}, size: {}",
                                marketDataSequenceId,
                                msg.getSequence(),
                                msg.getPrice(),
                                msg.getSize());
                        updateOB(msg);
                    });
                    return null;
                }

                @Override
                public void done() {
                    repaint();
                    updateUI();
                }
            };
            websocketFeedStarter.execute();
        }
    }

    private JSplitPane getSplitScrollableTables(JScrollPane scrollableAsks, JScrollPane scrollableBids) {
        JSplitPane splitPane = new JSplitPane(VERTICAL_SPLIT, scrollableAsks, scrollableBids);
        int height = orderBook.getHeight() / 2;
        splitPane.setDividerLocation(height);
        return splitPane;
    }

    private void openWebsocket(OrderBookView orderBook) throws InterruptedException {
        log.info("*** Opening To Websocket ***");
        websocketFeed.connect();

        // Messages are handled here:
        WebsocketMessageHandler messageHandler = new WebsocketMessageHandler(orderBook, objectMapper);
        websocketFeed.addMessageHandler(messageHandler);

        Subscribe subscribeRequest = new Subscribe(new String[]{productId}); // full channel by default
        String signedSubscribeMsg = websocketFeed.signObject(subscribeRequest);
        websocketFeed.sendMessage(signedSubscribeMsg);
        Thread.sleep(1000);
    }

    private void setMaxSequenceId(Long sequenceId) {
        this.maxSequenceId = sequenceId;
    }

    private JScrollPane addToScrollPane(String side, JTable table) {
        JScrollPane jScrollPane = new JScrollPane(table);
        scrollPanes.put(side + "_" + productId, jScrollPane);
        return jScrollPane;
    }

    private JTable getBidTable() {
        log.info("Populating BID table for: " + productId);
        JTable bidTable = initTable(bids);
        bidAskTables.put("buy_" + productId, bidTable);
        return bidTable;
    }

    private JTable getAskTable() {
        log.info("Populating ASK table for: " + productId);
        JTable askTable = initTable(asks);
        bidAskTables.put("sell_" + productId, askTable);
        return askTable;
    }

    private MarketData getMarketData() {
        MarketData marketData = marketDataService.getMarketDataOrderBook(productId, FULL_ORDER_BOOK);
        Collections.sort(marketData.getAsks());
        bids = new LinkedList<>(marketData.getBids());
        asks = new LinkedList<>(marketData.getAsks());
        return marketData;
    }

    private JTable initTable(List<OrderItem> marketData) {
        OrderBookModel model = new OrderBookModel();

        int index = 0;
        for (OrderItem item : marketData) {
            log.info("Inserting new OrderItem row at index: {}, price: {}, size: {}, qty: {}", index, item.getPrice(), item.getSize(), item.getNum());
            model.insertRowAt(item, index);
            index++;
        }

        JTable table = new JTable(model);
        table.setFont(new Font("Calibri", Font.PLAIN, 10));
        return table;
    }

    public void heartBeat(HeartBeat heartBeat) {
        isWebsocketAlive = true;
    }

    /**
     * @param msg
     */
    public void updateOB(OrderBookMessage msg) {
        log.info("OrderBookView.updateOrderBook");
        OrderBookModel model = ((OrderBookModel) bidAskTables.get(msg.getSide() + "_" + msg.getProduct_id()).getModel());

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

    public void setLimitOrderBookViewer(JPanel splitPane) {
        rootPanel.removeAll();
        rootPanel.add(splitPane);
        repaint();
        updateUI();
    }


    public void switchProduct(String productId) {
        isOrderbookReady = false;
        this.productId = productId;
        rootPanel.removeAll();
        load();
        // TODO open new tab / Replace existing panel?
    }

    public boolean isOrderBookReady() {
        return isOrderbookReady;
    }
}
