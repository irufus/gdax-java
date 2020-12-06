package com.coinbase.exchange.websocketfeed;

import lombok.Data;
import lombok.EqualsAndHashCode;

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
@Data
@EqualsAndHashCode(callSuper = true)
public class ErrorOrderBookMessage extends FeedMessage {

    private String message;

    public ErrorOrderBookMessage() {
        setType("error");
    }

    public ErrorOrderBookMessage(String message) {
        this();
        this.message = message;
    }

    //    @JsonCreator
//    public ErrorOrderBookMessage(String type, Long sequence, Instant time, String product_id, String message) {
//        super(type, sequence, time, product_id);
//        this.message = message;
//    }
}
