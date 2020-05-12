package com.coinbase.exchange.gui.websocketfeed;

import com.coinbase.exchange.api.exchange.Signature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;

/**
 * Websocketfeed adapted from someone else's code
 * <p>
 * >Jiji Sasidharan
 */
@ClientEndpoint
public class WebsocketFeed {

    static final Logger log = LoggerFactory.getLogger(WebsocketFeed.class);

    final String websocketUrl;
    final String passphrase;
    final String key;
    final boolean guiEnabled;
    final Signature signature;

    Session userSession;
    MessageHandler messageHandler;
    ObjectMapper objectMapper;

    public WebsocketFeed(String websocketUrl,
                         String key,
                         String passphrase,
                         boolean guiEnabled,
                         Signature signature,
                         ObjectMapper objectMapper) {
        this.key = key;
        this.passphrase = passphrase;
        this.websocketUrl = websocketUrl;
        this.signature = signature;
        this.guiEnabled = guiEnabled;
        this.objectMapper = objectMapper;

        if (guiEnabled) {
            try {
                WebSocketContainer container = ContainerProvider.getWebSocketContainer();
                container.connectToServer(this, new URI(websocketUrl));
            } catch (DeploymentException | IOException e) {
                log.error("Could not connect to remote server: " + e.getMessage() + ", " + e.getLocalizedMessage(), e);
            } catch (URISyntaxException e) {
                log.error("Coinbase Pro MalFormed URL", e);
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
        log.info("opened websocket");
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
        log.info("closing websocket {}", reason);
        this.userSession = null;
    }

    @OnError
    public void onError(Throwable t) {
        log.error("websocket error", t);
    }


    /**
     * Callback hook for OrderBookMessage Events. This method will be invoked when a client send a com.coinbase.exchange.api.websocketfeed.message.
     *
     * @param message The text com.coinbase.exchange.api.websocketfeed.message
     */
    @OnMessage
    public void onMessage(String message) {
        if (this.messageHandler != null) {
            this.messageHandler.handleMessage(message);
        }
    }

    /**
     * register com.coinbase.exchange.api.websocketfeed.message handler
     *
     * @param msgHandler
     */
    public void addMessageHandler(MessageHandler msgHandler) {
        this.messageHandler = msgHandler;
    }

    /**
     * Send a com.coinbase.exchange.api.websocketfeed.message.
     *
     * @param message
     */
    public void sendMessage(String message) {
        log.info("send: " + message);
        this.userSession.getAsyncRemote().sendText(message);
    }


    public void subscribe(Subscribe msg) {
        String jsonSubscribeMessage = signObject(msg);
        log.info(jsonSubscribeMessage);
        // send subscription com.coinbase.exchange.api.websocketfeed.message to websocket
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

    /**
     * OrderBookMessage handler.
     *
     * @author Jiji_Sasidharan
     */
    public interface MessageHandler {
        public void handleMessage(String message);
    }
}
