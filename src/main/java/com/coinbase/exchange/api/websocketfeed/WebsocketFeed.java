package com.coinbase.exchange.api.websocketfeed;

import com.coinbase.exchange.api.exchange.Signature;
import com.coinbase.exchange.api.gui.orderbook.OrderBook;
import com.coinbase.exchange.api.websocketfeed.message.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.time.Instant;

/**
 * Websocketfeed adapted from someone else's code
 *
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
    boolean guiEnabled;

    @Autowired
    public WebsocketFeed(@Value("${websocket.baseUrl}") String websocketUrl,
                         @Value("${gdax.key") String key,
                         @Value("${gdax.passphrase}") String passphrase,
                         @Value("${gui.enabled") String guiEnabled,
                         Signature signature){
        this.key = key;
        this.passphrase = passphrase;
        this.websocketUrl = websocketUrl;
        this.signature = signature;
        this.guiEnabled = Boolean.parseBoolean(guiEnabled);

        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, new URI(websocketUrl));
        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }
    /**
     * Callback hook for Connection open events.
     *
     * @param userSession the userSession which is opened.
     */
    @OnOpen
    public void onOpen(Session userSession) {
        this.userSession = userSession;
    }

    /**
     * Callback hook for Connection close events.
     *
     * @param userSession the userSession which is getting closed.
     * @param reason the reason for connection close
     */
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        System.out.println("closing websocket");
        this.userSession = null;
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
        this.userSession.getAsyncRemote().sendText(message);
    }


    public void subscribe(Subscribe message, OrderBook orderBook) {
        String jsonSubscribeMessage = signObject(message);

        try {
            addMessageHandler(new MessageHandler() {

                @Override
                public void handleMessage(String json) {
                    OrderBookMessage message = getObject(json, new TypeReference<OrderBookMessage>() {
                    });
                    String type = message.getType();

                    if (type.equals("heartbeat")) {
                        log.info("heartbeat");
                        orderBook.heartBeat(getObject(json, new TypeReference<HeartBeat>() {}));
                    } else if (type.equals("received")) {
                        log.info("order received");
                        orderBook.updateOrderBook(getObject(json, new TypeReference<OrderReceivedOrderBookMessage>() {}));
                    } else if (type.equals("open")) {
                        log.info("Order opened");
//                        orderBook.updateOrderBook(getObject(json, new TypeReference<OrderOpenOrderBookMessage>() {}));
                    } else if (type.equals("done")) {
                        log.info("Order done");
                        //     orderBook.updateOrderBook(getObject(json, new TypeReference<OrderDoneOrderBookMessage>(){}));
                    } else if (type.equals("match")) {
                        log.info("Order matched!!");
                        //     orderBook.updateOrderBook(getObject(json, new TypeReference<OrderMatchOrderBookMessage>(){}));
                    } else if (type.equals("change")) {
                        log.info("Update");
                        //     orderBook.updateOrderBookWithChange(getObject(json, new TypeReference<OrderChangeOrderBookMessage>(){}));
                    } else {
                        // ERROR
                        log.info("Error");
                        //     orderBook.orderBookError(getObject(json, new TypeReference<ErrorOrderBookMessage>(){}));
                    }

                }
            });

            // send message to websocket
            sendMessage(jsonSubscribeMessage);

            Thread.sleep(1000);

        } catch (InterruptedException ex) {
            System.err.println("InterruptedException exception: " + ex.getMessage());
        }
    }

    public String signObject(Subscribe jsonObj) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(jsonObj);

        String timestamp = Instant.now().getEpochSecond() + "";
        jsonObj.setKey(key);
        jsonObj.setTimestamp(timestamp);
        jsonObj.setPassphrase(passphrase);
        jsonObj.setSignature(signature.generate("", "GET", jsonString, timestamp));

        return gson.toJson(jsonObj);
    }

    public <T> T getObject(String json, TypeReference<T> type){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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