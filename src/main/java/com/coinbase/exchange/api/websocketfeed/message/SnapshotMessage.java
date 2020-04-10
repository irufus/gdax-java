package com.coinbase.exchange.api.websocketfeed.message;

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
public class SnapshotMessage extends OrderBookMessage {
    private String product_id;
    private String[][] bids;
    private String[][] asks;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

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
