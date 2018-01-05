package com.coinbase.exchange.api.websocketfeed;

import com.coinbase.exchange.api.exchange.Signature;
import com.coinbase.exchange.api.gui.orderbook.OrderBookView;
import com.coinbase.exchange.api.websocketfeed.message.*;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.websocket.*;
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
    boolean guiEnabled;

    @Autowired
    public WebsocketFeed(@Value("${websocket.baseUrl}") String websocketUrl,
                         @Value("${gdax.key}") String key,
                         @Value("${gdax.passphrase}") String passphrase,
                         @Value("${gui.enabled}") boolean guiEnabled,
                         Signature signature) {
        this.key = key;
        this.passphrase = passphrase;
        this.websocketUrl = websocketUrl;
        this.signature = signature;
        this.guiEnabled = guiEnabled;

        if (guiEnabled) {
            try {
                WebSocketContainer container = ContainerProvider.getWebSocketContainer();
                container.connectToServer(this, new URI(websocketUrl));
            } catch (Exception e) {
                System.out.println("Could not connect to remote server: " + e.getMessage() + ", " + e.getLocalizedMessage());
                e.printStackTrace();
            }
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
     * @param reason      the reason for connection close
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


    public void subscribe(Subscribe msg, OrderBookView orderBook) {
        String jsonSubscribeMessage = signObject(msg);

        addMessageHandler(json -> {

            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                public Void doInBackground() {
                    OrderBookMessage message = getObject(json, OrderBookMessage.class);

                    switch (message.getType()){
                        case "heartbeat":
                            log.info("heartbeat");
                            orderBook.heartBeat(getObject(json, HeartBeat.class));
                            break;
                        case "received":
                            // received orders are not necessarily live orders - so I'm ignoring these msgs as they're
                            // subject to change.
                            log.info("order received {}", json);
                            break;
                        case "open":
                            log.info("Order opened: " + json );
                            orderBook.updateOrderBook(getObject(json, OrderOpenOrderBookMessage.class));
                            break;
                        case "done":
                            log.info("Order done: " + json);
                            if (!message.getReason().equals("filled")) {
                                OrderBookMessage doneOrder = getObject(json, OrderDoneOrderBookMessage.class);
                                orderBook.updateOrderBook(doneOrder);
                            }
                            break;
                        case "match":
                            log.info("Order matched: " + json);
                            OrderBookMessage matchedOrder = getObject(json, OrderMatchOrderBookMessage.class);
                            orderBook.updateOrderBook(matchedOrder);
                            break;
                        case "change":
                            // TODO - possibly need to provide implementation for this to work in real time.
                            log.info("Order Changed {}", json);
                            // orderBook.updateOrderBookWithChange(getObject(json, OrderChangeOrderBookMessage.class));
                            break;
                        default:
                            // Not sure this is required unless I'm attempting to place orders
                            // ERROR
                            log.error("Error {}", json);
                            // orderBook.orderBookError(getObject(json, ErrorOrderBookMessage.class));
                    }
                    return null;
                }

                public void done() {

                }
            };
            worker.execute();
        });

        // send message to websocket
        sendMessage(jsonSubscribeMessage);

    }

    // TODO - get this into postHandle interceptor.
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

    public <T> T getObject(String json, Class<T> type) {
            Gson gson = new Gson();
            return gson.fromJson(json, type);
    }

    /**
     * OrderBookMessage handler.
     *
     * @author Jiji_Sasidharan
     */
    public interface MessageHandler {
        void handleMessage(String message);
    }
}
