package com.coinbase.exchange.websocketfeed;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@Data
@EqualsAndHashCode(callSuper = true)
public class ActivateOrderBookMessage extends OrderBookMessage {
    @JsonProperty("stop_type")
    private String stopType;
    @JsonProperty("stop_price")
    private BigDecimal stopPrice;
    @JsonProperty("timestamp")
    private Instant timestamp;

    public ActivateOrderBookMessage() {
        setType("activate");
    }

    public ActivateOrderBookMessage(String stopType, BigDecimal stopPrice, Instant timestamp, boolean privateFlag) {
        this();
        this.stopType = stopType;
        this.stopPrice = stopPrice;
        this.timestamp = timestamp;
        this.privateFlag = privateFlag;
    }

    @JsonProperty("private")
    private boolean privateFlag;

}
