package com.coinbase.exchange.api.websocketfeed.message;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * An activate message is sent when a stop order is placed.
 * When the stop is triggered the order will be placed and go through the order lifecycle.
 * Example:
 * <pre>
 * {
 *   "type": "activate",
 *   "product_id": "test-product",
 *   "timestamp": "1483736448.299000",
 *   "user_id": "12",
 *   "profile_id": "30000727-d308-cf50-7b1c-c06deb1934fc",
 *   "order_id": "7b52009b-64fd-0a2a-49e6-d8a939753077",
 *   "stop_type": "entry",
 *   "side": "buy",
 *   "stop_price": "80",
 *   "size": "2",
 *   "funds": "50",
 *   "private": true
 * }
 * </pre>
 */
public class ActivateOrderBookMessage extends OrderBookMessage {
    private String stop_type;
    private BigDecimal stop_price;
    private Instant timestamp;

    @JsonProperty("private")
    private boolean privateFlag;

    public String getStop_type() {
        return stop_type;
    }

    public void setStop_type(String stop_type) {
        this.stop_type = stop_type;
    }

    public BigDecimal getStop_price() {
        return stop_price;
    }

    public void setStop_price(BigDecimal stop_price) {
        this.stop_price = stop_price;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isPrivateFlag() {
        return privateFlag;
    }

    public void setPrivateFlag(boolean privateFlag) {
        this.privateFlag = privateFlag;
    }
}
