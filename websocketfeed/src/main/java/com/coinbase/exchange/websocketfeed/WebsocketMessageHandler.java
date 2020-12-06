package com.coinbase.exchange.websocketfeed;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.stream.Collectors.toList;

public class WebsocketMessageHandler implements WebsocketFeed.MessageHandler {

    private static final String FILLED = "filled";
    private static final Logger log = LoggerFactory.getLogger(WebsocketMessageHandler.class);

    private final ObjectMapper objectMapper;
    private final List<OrderBookMessage> orderMessageQueue;

    public WebsocketMessageHandler(final ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
        this.orderMessageQueue = new ArrayList<>();
    }

    @Override
    public void handleMessage(final String json) {
        CompletableFuture.runAsync(() -> {
            FeedMessage message = getObject(json, FeedMessage.class);

            if (message instanceof OrderBookMessage) {
                orderMessageQueue.add((OrderBookMessage) message);
            } else {
                log.warn("IGNORED MESSAGE: {}", message);
            }

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
        });
    }

    @Override
    public List<OrderBookMessage> getQueuedMessages(Long sequenceId) {
        return orderMessageQueue.stream().filter(msg -> msg.getSequence().compareTo(sequenceId)<0).collect(toList());
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
    }

    private void handleDone(DoneOrderBookMessage message, String json) {
        log.info("DONE: {}", json);
        DoneOrderBookMessage doneOrder = message;
    }

    private void handleOpened(OpenedOrderBookMessage message, String json) {
        log.info("OPENED: {}", json);
        OpenedOrderBookMessage openOrderBookMessage = message;
    }

    private void handleReceived(String json) {
        // received orders are not necessarily live orders -
        // so safe to ignore these msgs as they're unnecessary
        // https://docs.pro.coinbase.com/#the-full-channel see here for more details

    }

    private void handleHeartbeat(HeartBeat message) {
        HeartBeat heartBeat = message;
        log.info("HEARTBEAT. Last trade id: {}", heartBeat.getLastTradeId());
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
