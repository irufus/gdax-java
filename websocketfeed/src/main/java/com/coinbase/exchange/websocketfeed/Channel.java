package com.coinbase.exchange.websocketfeed;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.coinbase.exchange.websocketfeed.ChannelName.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Channel {
	public static final Channel CHANNEL_HEARTBEAT = new Channel(heartbeat, null);
	public static final Channel CHANNEL_STATUS = new Channel(status, null);
	public static final Channel CHANNEL_TICKER = new Channel(ticker, null);
	public static final Channel CHANNEL_LEVEL2 = new Channel(level2, null);
	public static final Channel CHANNEL_USER = new Channel(user, null);
	public static final Channel CHANNEL_MATCHES = new Channel(matches, null);
	public static final Channel CHANNEL_FULL = new Channel(full, null);

	@JsonProperty("name")
	private ChannelName name;
	@JsonProperty("product_ids")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String[] product_ids;
}
