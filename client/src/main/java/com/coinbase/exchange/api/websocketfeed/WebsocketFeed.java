package com.coinbase.exchange.api.websocketfeed;

import com.coinbase.exchange.api.exchange.Signature;
import com.coinbase.exchange.api.websocketfeed.message.Subscribe;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
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


    public void subscribe(Subscribe msg) {
        String jsonSubscribeMessage = signObject(msg);

        // send subscription message to websocket
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
