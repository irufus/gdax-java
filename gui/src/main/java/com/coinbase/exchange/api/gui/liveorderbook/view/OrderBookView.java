package com.coinbase.exchange.api.gui.liveorderbook.view;

import com.coinbase.exchange.api.gui.liveorderbook.OrderBookModel;
import com.coinbase.exchange.api.marketdata.MarketData;
import com.coinbase.exchange.api.marketdata.MarketDataService;
import com.coinbase.exchange.api.marketdata.OrderItem;
import com.coinbase.exchange.api.websocketfeed.WebsocketFeed;
import com.coinbase.exchange.api.websocketfeed.message.HeartBeat;
import com.coinbase.exchange.api.websocketfeed.message.OrderBookMessage;
import com.coinbase.exchange.api.websocketfeed.message.OrderDoneOrderBookMessage;
import com.coinbase.exchange.api.websocketfeed.message.OrderMatchOrderBookMessage;
import com.coinbase.exchange.api.websocketfeed.message.OrderOpenOrderBookMessage;
import com.coinbase.exchange.api.websocketfeed.message.Subscribe;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * TODO - convert to JFX rather than using Swing.
 * <p>
 * Created by robevansuk on 10/03/2017.
 */
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

    public OrderBookView(boolean guiEnabled,
                         MarketDataService marketDataService,
                         WebsocketFeed websocketFeed) {
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
                SwingUtilities.invokeAndWait(() -> load(guiEnabled));
            } catch (InterruptedException | InvocationTargetException e) {
                log.error("Something went wrong whilst starting the OrderBookView", e);
            }
        }
    }

    /**
     * Gets the market data and loads in all the prices for the current order book,
     * then submits a subscribe message to the server so that price updates are received.
     */
    public void load(boolean guiEnabled) {
        OrderBookView thisOrderBook = this;
        if (guiEnabled) {
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                public Void doInBackground() {

                    log.info("*********** OrderBookView.load()");
                    for (String productId : productIds) {

                        log.info("******** Getting the view for: " + productId);
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
                    websocketFeed.subscribe(new Subscribe(productIds));
                    // TODO - extract to external standalone class rather than having this mess in-line.
                    websocketFeed.addMessageHandler(new WebsocketFeed.MessageHandler() {
                        @Override
                        public void handleMessage(String json) {
                            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                                @Override
                                public Void doInBackground() {
                                    OrderBookMessage message = getObject(json, new TypeReference<OrderBookMessage>() {});

                                    String type = message.getType();

                                    if (type.equals("heartbeat")) {
                                        log.info("heartbeat");
                                        thisOrderBook.heartBeat(getObject(json, new TypeReference<HeartBeat>() {}));
                                    } else if (type.equals("received")) {
                                        // received orders are not necessarily live orders - so I'm ignoring these msgs as they're
                                        // subject to change.
                                        log.info("order received {}", json);

                                    } else if (type.equals("open")) {
                                        log.info("Order opened: " + json);
                                        thisOrderBook.updateOrderBook(getObject(json, new TypeReference<OrderOpenOrderBookMessage>() {
                                        }));
                                    } else if (type.equals("done")) {
                                        log.info("Order done: " + json);
                                        if (!message.getReason().equals("filled")) {
                                            OrderBookMessage doneOrder = getObject(json, new TypeReference<OrderDoneOrderBookMessage>() {
                                            });
                                            thisOrderBook.updateOrderBook(doneOrder);
                                        }
                                    } else if (type.equals("match")) {
                                        log.info("Order matched: " + json);
                                        OrderBookMessage matchedOrder = getObject(json, new TypeReference<OrderMatchOrderBookMessage>() {
                                        });
                                        thisOrderBook.updateOrderBook(matchedOrder);
                                    } else if (type.equals("change")) {
                                        // TODO - possibly need to provide implementation for this to work in real time.
                                        log.info("Order Changed {}", json);
                                        // orderBook.updateOrderBookWithChange(getObject(json, new TypeReference<OrderChangeOrderBookMessage>(){}));
                                    } else {
                                        // Not sure this is required unless I'm attempting to place orders
                                        // ERROR
                                        log.info("Unrecognised message type {}", json);
                                        // orderBook.orderBookError(getObject(json, new TypeReference<ErrorOrderBookMessage>(){}));
                                    }
                                    return null;
                                }

                                public void done() {

                                }
                            };
                            worker.execute();
                        }
                    });
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
    }

    public <T> T getObject(String json, TypeReference<T> type) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            log.info(json);
            return objectMapper.readValue(json, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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

    public void heartBeat(HeartBeat heartBeat) {
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
