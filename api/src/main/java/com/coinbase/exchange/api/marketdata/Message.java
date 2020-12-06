package com.coinbase.exchange.api.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
	@JsonProperty("type")
	private String type;
	@JsonProperty("sequence")
	private Long sequence;
	@JsonProperty("order_id")
	private String orderId;
	@JsonProperty("size")
	private BigDecimal size;
	@JsonProperty("price")
	private BigDecimal price;
	@JsonProperty("side")
	private String side;
	@JsonProperty("remaining_size")
	private BigDecimal remainingSize;
	@JsonProperty("reason")
	private String reason;
	@JsonProperty("maker_order_id")
	private String makerOrderId;
	@JsonProperty("taker_order_id")
	private String takerOrderId;
	@JsonProperty("time")
	private String time;
}
