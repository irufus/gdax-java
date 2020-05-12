package com.coinbase.exchange.websocketfeed;

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
public class L2UpdateMessage extends FeedMessage {
    private String[][] changes;

    public String[][] getChanges() {
        return changes;
    }

    public void setChanges(String[][] changes) {
        this.changes = changes;
    }
}
