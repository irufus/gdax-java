package com.coinbase.exchange.websocketfeed;

/**
 * A snapshot of the order book
 * Example:
 * <pre>
 * {
 *     "type": "snapshot",
 *     "product_id": "BTC-USD",
 *     "bids": [["10101.10", "0.45054140"]],
 *     "asks": [["10102.55", "0.57753524"]]
 * }
 * </pre>
 */
public class SnapshotMessage extends FeedMessage {
    private String[][] bids;
    private String[][] asks;

    public String[][] getBids() {
        return bids;
    }

    public void setBids(String[][] bids) {
        this.bids = bids;
    }

    public String[][] getAsks() {
        return asks;
    }

    public void setAsks(String[][] asks) {
        this.asks = asks;
    }
}
