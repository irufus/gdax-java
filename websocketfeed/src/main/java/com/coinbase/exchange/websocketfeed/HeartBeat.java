package com.coinbase.exchange.websocketfeed;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * A message sent once a second when heartbeat is turned on.
 * <pre>
 * {
 *     "type": "heartbeat",                   // inherited
 *     "sequence": 90,                        // inherited
 *     "last_trade_id": 20,
 *     "product_id": "BTC-USD",               // inherited
 *     "time": "2014-11-07T08:19:28.464459Z"  // inherited
 * }
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HeartBeat extends FeedMessage {

    @JsonProperty("last_trade_id")
    private Long lastTradeId;

    public HeartBeat() {
        setType("heartbeat");
    }

    public HeartBeat(Long lastTradeId) {
        this();
        this.lastTradeId = lastTradeId;
    }
}
