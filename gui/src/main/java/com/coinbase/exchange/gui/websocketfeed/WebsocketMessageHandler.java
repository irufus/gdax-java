package com.coinbase.exchange.gui.websocketfeed;

import com.coinbase.exchange.gui.liveorderbook.view.OrderBookView;
import com.coinbase.exchange.websocketfeed.ChangedOrderBookMessage;
import com.coinbase.exchange.websocketfeed.DoneOrderBookMessage;
import com.coinbase.exchange.websocketfeed.ErrorOrderBookMessage;
import com.coinbase.exchange.websocketfeed.FeedMessage;
import com.coinbase.exchange.websocketfeed.HeartBeat;
import com.coinbase.exchange.websocketfeed.MatchedOrderBookMessage;
import com.coinbase.exchange.websocketfeed.OpenedOrderBookMessage;
import com.coinbase.exchange.websocketfeed.OrderBookMessage;
import com.coinbase.exchange.websocketfeed.ReceivedOrderBookMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class WebsocketMessageHandler implements WebsocketFeed.MessageHandler {

    public static final String FILLED = "filled";
    Logger log = LoggerFactory.getLogger(WebsocketMessageHandler.class);
    private final OrderBookView orderBookView;
    private final ObjectMapper objectMapper;

    private List<OrderBookMessage> orderMessageQueue;

    public WebsocketMessageHandler(final OrderBookView orderBookView,
                                   final ObjectMapper objectMapper){
        this.orderBookView = orderBookView;
        this.objectMapper = objectMapper;
        this.orderMessageQueue = new ArrayList<>();
    }

    @Override
    public void handleMessage(final String json) {
        SwingWorker<Void, Void> swingOrderBookMessageHandler = new SwingWorker<>() {
            @Override
            public Void doInBackground() {
                log.info("INCOMING: {}", json);
                FeedMessage message = getObject(json, FeedMessage.class);

                if (!orderBookView.isOrderBookReady()) {
                    if (message instanceof OrderBookMessage) {
                        orderMessageQueue.add((OrderBookMessage) message);
                    } else {
                        log.warn("IGNORED MESSAGE: {}", message);
                    }

                } else {
                    if (message instanceof HeartBeat) {
                        handleHeartbeat((HeartBeat) message);

                    } else if (message instanceof ReceivedOrderBookMessage) {
                        handleReceived(json);

                    } else if (message instanceof OpenedOrderBookMessage) {
                        handleOpened((OpenedOrderBookMessage) message, json);

                    } else if (message instanceof DoneOrderBookMessage) {
                        handleDone((DoneOrderBookMessage) message, json);

                    } else if (message instanceof MatchedOrderBookMessage) {
                        handleMatched(json);

                    } else if (message instanceof ChangedOrderBookMessage) {
                        handleChanged(json);

                    } else if (message instanceof ErrorOrderBookMessage) {
                        handleError((ErrorOrderBookMessage) message, json);

                    } else {
                        log.error("Unsupported message type {}", json);
                    }
                }
                return null;
            }
        };
        swingOrderBookMessageHandler.execute();
    }

    @Override
    public List<OrderBookMessage> getQueuedMessages(Long sequenceId) {
        return orderMessageQueue.stream().filter(msg -> msg.getSequence()>sequenceId).collect(toList());
    }

    private void handleError(ErrorOrderBookMessage message, String json) {
        // Not sure this is required unless I'm attempting to place orders
        // ERROR
        log.warn("Error {}", json);
        ErrorOrderBookMessage errorMessage = message;
    }

    private void handleChanged(String json) {
        // TODO - possibly need to provide implementation for this to work in real time.
        log.info("CHANGED {}", json);
    }

    private void handleMatched(String json) {
        log.info("MATCHED: {}", json);
        OrderBookMessage matchedOrder = getObject(json, MatchedOrderBookMessage.class);
        orderBookView.updateOrderBook(matchedOrder);
    }

    private void handleDone(DoneOrderBookMessage message, String json) {
        log.info("DONE: {}", json);
        DoneOrderBookMessage doneOrder = message;
        if (!doneOrder.getReason().equals(FILLED)) {
            orderBookView.updateOrderBook(doneOrder);
        }
    }

    private void handleOpened(OpenedOrderBookMessage message, String json) {
        log.info("OPENED: {}", json);
        OpenedOrderBookMessage openOrderBookMessage = message;
        orderBookView.updateOrderBook(openOrderBookMessage);
    }

    private void handleReceived(String json) {
        // received orders are not necessarily live orders -
        // so safe to ignore these msgs as they're unnecessary
        // https://docs.pro.coinbase.com/#the-full-channel see here for more details
        log.info("RECEIVED {}", json);
    }

    private void handleHeartbeat(HeartBeat message) {
        HeartBeat heartBeat = message;
        orderBookView.heartBeat(heartBeat);
        log.info("HEARTBEAT. Last trade id: {}", heartBeat.getLast_trade_id());
    }


    public <T> T getObject(String json, Class<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (IOException e) {
            log.error("Parsing {} Failed: {}", type, e);
        }
        return null;
    }
}
