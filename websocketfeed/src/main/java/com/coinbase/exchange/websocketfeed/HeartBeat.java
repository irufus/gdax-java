package com.coinbase.exchange.websocketfeed;

/**
 * A com.coinbase.exchange.api.websocketfeed.message sent once a second when heartbeat is turned on.
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
public class HeartBeat extends FeedMessage {

    private String last_trade_id;

    public String getLast_trade_id() {
        return last_trade_id;
    }

    public void setLast_trade_id(String last_trade_id) {
        this.last_trade_id = last_trade_id;
    }
}
