package com.coinbase.exchange.api.websocketfeed.message;

import java.time.Instant;

/**
 * Subsequent updates will have the type l2update.
 * The changes property of l2updates is an array with [side, price, size] tuples.
 * The time property of l2update is the time of the event as recorded by our trading engine. Please note that size is the updated size at that price level, not a delta. A size of "0" indicates the price level can be removed.
 * Example:
 * <pre>
 * {
 *   "type": "l2update",
 *   "product_id": "BTC-GBP",
 *   "changes": [
 *     [
 *       "buy",
 *       "5454.12",
 *       "0.00000000"
 *     ]
 *   ],
 *   "time": "2020-04-10T15:28:07.393966Z"
 * }
 * </pre>
 */
public class L2UpdateMessage extends OrderBookMessage {
    private String product_id;
    private String[][] changes;
    private Instant time;


    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String[][] getChanges() {
        return changes;
    }

    public void setChanges(String[][] changes) {
        this.changes = changes;
    }

    @Override
    public Instant getTime() {
        return time;
    }

    @Override
    public void setTime(Instant time) {
        this.time = time;
    }
}
