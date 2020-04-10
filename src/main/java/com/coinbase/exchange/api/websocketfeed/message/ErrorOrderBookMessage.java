package com.coinbase.exchange.api.websocketfeed.message;

/**
 * If you send a message that is not recognized or an error
 * occurs, the error message will be sent and you will be
 * disconnected.
 * <pre>
 * {
 *     "type": "error",
 *     "message": "error message"
 * }
 * </pre>
 */
public class ErrorOrderBookMessage extends OrderBookMessage {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
