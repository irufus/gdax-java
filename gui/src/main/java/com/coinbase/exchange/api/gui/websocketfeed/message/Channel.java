package com.coinbase.exchange.api.websocketfeed.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.coinbase.exchange.api.websocketfeed.message.ChannelName.heartbeat;
import static com.coinbase.exchange.api.websocketfeed.message.ChannelName.level2;
import static com.coinbase.exchange.api.websocketfeed.message.ChannelName.matches;
import static com.coinbase.exchange.api.websocketfeed.message.ChannelName.status;
import static com.coinbase.exchange.api.websocketfeed.message.ChannelName.ticker;
import static com.coinbase.exchange.api.websocketfeed.message.ChannelName.user;
import static com.coinbase.exchange.api.websocketfeed.message.ChannelName.full;

public class Channel {
    public static final Channel CHANNEL_HEARTBEAT = new Channel(heartbeat, null);
    public static final Channel CHANNEL_STATUS = new Channel(status, null);
    public static final Channel CHANNEL_TICKER = new Channel(ticker, null);
    public static final Channel CHANNEL_LEVEL2 = new Channel(level2, null);
    public static final Channel CHANNEL_USER = new Channel(user, null);
    public static final Channel CHANNEL_MATCHES = new Channel(matches, null);
    public static final Channel CHANNEL_FULL = new Channel(full, null);

    private ChannelName name;

    @JsonProperty("product_ids")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String[] product_ids;

    public Channel() {
    }

    public Channel(ChannelName name, String[] product_ids) {
        this.name = name;
        this.product_ids = product_ids;
    }

    public ChannelName getName() {
        return name;
    }

    public void setName(ChannelName name) {
        this.name = name;
    }

    public String[] getProduct_ids() {
        return product_ids;
    }

    public void setProduct_ids(String[] product_ids) {
        this.product_ids = product_ids;
    }
}
