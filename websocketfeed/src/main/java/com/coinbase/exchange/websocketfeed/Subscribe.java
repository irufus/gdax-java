package com.coinbase.exchange.websocketfeed;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import static com.coinbase.exchange.websocketfeed.ChannelName.full;

/**
 * To begin receiving feed messages, you must first send a subscribe message
 * indicating which channels and products to receive.
 * Example:
 * Subscribe to ETH-USD and ETH-EUR with the level2, heartbeat and ticker channels,
 * plus receive the ticker entries for ETH-BTC and ETH-USD
 * <pre>
 * {
 *   "type": "subscribe",
 *   "product_ids": [
 *     "ETH-USD",
 *     "ETH-EUR"
 *   ],
 *   "channels": [
 *     "level2",
 *     "heartbeat",
 *     {
 *       "name": "ticker",
 *       "product_ids": [
 *         "ETH-BTC",
 *         "ETH-USD"
 *       ]
 *     }
 *   ]
 * }
 * </pre>
 *
 * See docs https://docs.pro.coinbase.com/#subscribe
 */
@Getter
public class Subscribe {

    private final static String SUBSCRIBE_MSG_TYPE = "subscribe";

    @JsonProperty("type")
    private String type = SUBSCRIBE_MSG_TYPE;
    @JsonProperty("product_ids")
    private String[] productIds;
    @JsonProperty("channels")
    private Channel[] channels;

    // Used for signing the subscribe message to the Websocket feed
    @JsonProperty("signature")
    private String signature;
    @JsonProperty("passphrase")
    private String passphrase;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("apiKey")
    private String apiKey;

    public Subscribe() { }

    public Subscribe(String[] productIds) {
        this.productIds = productIds;
        this.channels = new Channel[]{new Channel(full, productIds)};
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getProductIds() {
        return productIds;
    }

    public void setProductIds(String[] productIds) {
        this.productIds = productIds;
    }

    public Subscribe setSignature(String signature) {
        this.signature = signature;
        return this;
    }

    public Subscribe setPassphrase(String passphrase) {
        this.passphrase = passphrase;
        return this;
    }

    public Subscribe setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Subscribe setKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public void setChannels(Channel[] channels) {
        this.channels = channels;
    }

    public Channel[] getChannels() {
        return channels;
    }
}
