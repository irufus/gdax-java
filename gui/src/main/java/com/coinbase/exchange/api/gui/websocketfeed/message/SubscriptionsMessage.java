package com.coinbase.exchange.api.websocketfeed.message;

/**
 * <pre>
 * {
 *     "type": "subscriptions",
 *     "channels": [
 *         {
 *             "name": "level2",
 *             "product_ids": [
 *                 "ETH-USD",
 *                 "ETH-EUR"
 *             ],
 *         },
 *         {
 *             "name": "heartbeat",
 *             "product_ids": [
 *                 "ETH-USD",
 *                 "ETH-EUR"
 *             ],
 *         },
 *         {
 *             "name": "ticker",
 *             "product_ids": [
 *                 "ETH-USD",
 *                 "ETH-EUR",
 *                 "ETH-BTC"
 *             ]
 *         }
 *     ]
 * }
 * </pre>
 */
public class SubscriptionsMessage extends FeedMessage {
    private Channel[] channels;

    public Channel[] getChannels() {
        return channels;
    }

    public void setChannels(Channel[] channels) {
        this.channels = channels;
    }
}
