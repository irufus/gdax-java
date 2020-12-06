package com.coinbase.exchange.websocketfeed;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SubscriptionsMessage extends FeedMessage {
    @JsonProperty("channels")
    private Channel[] channels;
}
