package com.coinbase.exchange.websocketfeed;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Generic message that will be passed as an argument to other message types
 * so the relevant parts can be determined and the messages typed.
 * Created by robevansuk on 15/03/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderBookMessage extends FeedMessage implements Comparable<OrderBookMessage> {


	@JsonProperty("trade_id")
	private String tradeId;
	@JsonProperty("side")
	private String side;
	@JsonProperty("order_id")
	private String orderId;
	@JsonProperty("order_type")
	private String orderType;

	@JsonProperty("funds")
	private BigDecimal funds;

	@JsonProperty("size")
	private BigDecimal size;
	@JsonProperty("price")
	private BigDecimal price;

	@JsonProperty("new_size")
	private BigDecimal newSize;
	@JsonProperty("old_size")
	private BigDecimal oldSize;
	@JsonProperty("new_funds")
	private BigDecimal newFunds;
	@JsonProperty("old_funds")
	private BigDecimal oldFunds;

	@JsonProperty("reason")
	private String reason;
	@JsonProperty("remaining_size")
	private BigDecimal remainingSize;

	@JsonProperty("maker_order_id")
	private String makerOrderId;
	@JsonProperty("taker_order_id")
	private String takerOrderId;
	@JsonProperty("taker_user_id")
	private String takerUserId;
	@JsonProperty("user_id")
	private String userId;
	@JsonProperty("taker_profile_id")
	private String takerProfileId;
	@JsonProperty("profile_id")
	private String profileId;

	@JsonProperty("last_trade_id")
	private String lastTradeId;

	@JsonProperty("client_oid")
	private String clientOid;
	@JsonProperty("stp")
	private String stp;

	@JsonProperty("message")
	private String message;
	@JsonProperty("open_24h")
	private String open24H;
	@JsonProperty("volume_24h")
	private String volume24H;
	@JsonProperty("low_24h")
	private String low24H;
	@JsonProperty("high_24h")
	private String high24H;
	@JsonProperty("volume_30d")
	private String volume30D;
	@JsonProperty("best_bid")
	private String bestBid;
	@JsonProperty("best_ask")
	private String bestAsk;
	@JsonProperty("last_size")
	private String lastSize;

	@JsonProperty("channels")
	private Channel[] channels;

	@Override
	public int compareTo(OrderBookMessage other) {
		return this.getSequence().compareTo(other.getSequence());
	}

	@Override
	public String toString() {
		return "OrderBookMessage{" +
				"side='" + side + '\'' +
				", type='" + getType() + '\'' +
				", size=" + size +
				", price=" + getPrice() +
				", remaining_size=" + remainingSize +
				", sequence=" + getSequence() +
				'}';
	}

	public static class OrderBookMessageBuilder {
		private final OrderBookMessage message = new OrderBookMessage();

		public OrderBookMessageBuilder setType(String type) {
			message.setType(type);
			return this;
		}

		public OrderBookMessageBuilder setSide(String side) {
			message.setSide(side);
			return this;
		}

		public OrderBookMessageBuilder setPrice(BigDecimal price) {
			message.setPrice(price);
			return this;
		}

		public OrderBookMessageBuilder setSize(BigDecimal size) {
			message.setSize(size);
			return this;
		}

		public OrderBookMessageBuilder setRemainingSize(BigDecimal remainingSize) {
			message.setRemainingSize(remainingSize);
			return this;
		}

		public OrderBookMessageBuilder setSequence(Long id) {
			message.setSequence(id);
			return this;
		}

		public OrderBookMessage build() {
			return message;
		}
	}
}
