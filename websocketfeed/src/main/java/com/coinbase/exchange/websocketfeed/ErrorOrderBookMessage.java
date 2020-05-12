package com.coinbase.exchange.websocketfeed;

/**
 * If you send a com.coinbase.exchange.api.websocketfeed.message that is not recognized or an error
 * occurs, the error com.coinbase.exchange.api.websocketfeed.message will be sent and you will be
 * disconnected.
 * <pre>
 * {
 *     "type": "error",
 *     "com.coinbase.exchange.api.websocketfeed.message": "error com.coinbase.exchange.api.websocketfeed.message"
 * }
 * </pre>
 */
public class ErrorOrderBookMessage extends FeedMessage {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
