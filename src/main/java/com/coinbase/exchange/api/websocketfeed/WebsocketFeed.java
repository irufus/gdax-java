package com.coinbase.exchange.api.websocketfeed;

import com.coinbase.exchange.api.exchange.Signature;
import com.coinbase.exchange.api.gui.orderbook.OrderBookView;
import com.coinbase.exchange.api.websocketfeed.message.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.time.Instant;

/**
 * Websocketfeed adapted from someone else's code
 * <p>
 * >Jiji Sasidharan
 */
@Component
@ClientEndpoint
public class WebsocketFeed {

    static Logger log = LoggerFactory.getLogger(WebsocketFeed.class);

    Signature signature;

    Session userSession = null;
    MessageHandler messageHandler;

    String websocketUrl;
    String passphrase;
    String key;
    ObjectMapper objectMapper;
  
    @Autowired
    public WebsocketFeed(@Value("${websocket.baseUrl}") String websocketUrl,
                         @Value("${gdax.key}") String key,
                         @Value("${gdax.passphrase}") String passphrase,
                         ObjectMapper objectMapper,
                         Signature signature) {
        this.key = key;
        // TODO replace with @Nonnull if these values are essential
        if (key != null && !key.isBlank()) {
          // fail-fast if key is missing
          throw new IllegalStateException("API key missing from configuration gdax.key");
        }
        this.passphrase = passphrase;
        this.websocketUrl = websocketUrl;
        this.signature = signature;
        this.objectMapper = objectMapper;
    }

    public void connect() {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.setDefaultMaxBinaryMessageBufferSize(1024 * 1024);
            container.setDefaultMaxTextMessageBufferSize(1024 * 1024);
            container.connectToServer(this, new URI(websocketUrl));
        } catch (Exception e) {
            log.error("Could not connect to remote server", e);
        }
    }

    /**
     * Callback hook for Connection open events.
     *
     * @param userSession the userSession which is opened.
     */
    @OnOpen
    public void onOpen(Session userSession) {
        log.info("opened websocket");
        this.userSession = userSession;
    }

    /**
     * Callback hook for Connection close events.
     *
     * @param reason      the reason for connection close
     */
    @OnClose
    public void onClose(CloseReason reason) {
        log.info("closing websocket " + reason);
        this.userSession = null;
    }

    @OnError
    public void onError(Throwable t) {
        log.error("websocket error", t);
    }

    /**
     * Callback hook for OrderBookMessage Events. This method will be invoked when a client send a message.
     *
     * @param message The text message
     */
    @OnMessage
    public void onMessage(String message) {
        if (this.messageHandler != null) {
            this.messageHandler.handleMessage(message);
        }
    }

    /**
     * register message handler
     *
     * @param msgHandler
     */
    public void addMessageHandler(MessageHandler msgHandler) {
        this.messageHandler = msgHandler;
    }

    /**
     * Send a message.
     *
     * @param message
     */
    public void sendMessage(String message) {
        log.info("send: " + message);
        this.userSession.getAsyncRemote().sendText(message);
    }


    public void subscribe(Subscribe subscribeReq, OrderBookView orderBook) {
        addMessageHandler(json -> {

            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                public Void doInBackground() {
                    log.info("received: " + json);
                    FeedMessage message = getObject(json, FeedMessage.class);

                    if (message instanceof HeartBeat)
                    {
                        log.info("heartbeat");
                        HeartBeat heartBeat = (HeartBeat) message;
                        orderBook.heartBeat(heartBeat);
                    }
                    else if (message instanceof OrderReceivedOrderBookMessage)
                    {
                        // received orders are not necessarily live orders - so I'm ignoring these msgs as they're
                        // subject to change.
                        log.info("order received {}", json);

                    }
                    else if (message instanceof OrderOpenOrderBookMessage)
                    {
                        log.info("Order opened: " + json );
                        OrderOpenOrderBookMessage openOrderBookMessage = (OrderOpenOrderBookMessage) message;
                        orderBook.updateOrderBook(openOrderBookMessage);
                    }
                    else if (message instanceof OrderDoneOrderBookMessage)
                    {
                        log.info("Order done: " + json);
                        OrderBookMessage doneOrder = (OrderDoneOrderBookMessage) message;
                        if (!doneOrder.getReason().equals("filled")) {
                            orderBook.updateOrderBook(doneOrder);
                        }
                    }
                    else if (message instanceof  OrderMatchOrderBookMessage)
                    {
                        log.info("Order matched: " + json);
                        OrderBookMessage matchedOrder = (OrderBookMessage) message;
                        orderBook.updateOrderBook(matchedOrder);
                    }
                    else if (message instanceof  OrderChangeOrderBookMessage)
                    {
                        // TODO - possibly need to provide implementation for this to work in real time.
                        log.info("Order Changed {}", json);
                        OrderChangeOrderBookMessage changeOrderMessage = (OrderChangeOrderBookMessage) message;
                        // orderBook.updateOrderBookWithChange(changeOrderMessage);
                    }
                    else if (message instanceof  ErrorOrderBookMessage)
                    {
                        // Not sure this is required unless I'm attempting to place orders
                        // ERROR
                        log.error("Error {}", json);
                        ErrorOrderBookMessage errorMessage = (ErrorOrderBookMessage) message;
                        // orderBook.orderBookError(errorMessage);
                    } else {
                        log.warn("Unsupported message " + message);
                    }
                    return null;
                }
            };
            worker.execute();
        });

        // send message to websocket
        String jsonSubscribeMessage = signObject(subscribeReq);
        sendMessage(jsonSubscribeMessage);
    }

    // TODO - get this into postHandle interceptor.
    public String signObject(Subscribe jsonObj) {

        String jsonString = toJson(jsonObj);

        String timestamp = Instant.now().getEpochSecond() + "";
        jsonObj.setKey(key);
        jsonObj.setTimestamp(timestamp);
        jsonObj.setPassphrase(passphrase);
        jsonObj.setSignature(signature.generate("", "GET", jsonString, timestamp));

        return toJson(jsonObj);
    }

    public <T> T getObject(String json, Class<T> type) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.readValue(json, type);
        } catch (IOException e) {
            log.error("Parsing failed", e);
        }
        return null;
    }

    private String toJson(Object object) {
        try {
            String json = objectMapper.writeValueAsString(object);
            return json;
        } catch (JsonProcessingException e) {
            log.error("Unable to serialize", e);
            throw new RuntimeException("Unable to serialize");
        }
    }

    /**
     * OrderBookMessage handler.
     *
     * @author Jiji_Sasidharan
     */
    public interface MessageHandler {
        public void handleMessage(String message);
    }
}
