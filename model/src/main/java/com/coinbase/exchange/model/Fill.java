package com.coinbase.exchange.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Fill {
	@JsonProperty("trade_id")
	private Integer tradeId;
	@JsonProperty("product_id")
	private String productId;
	@JsonProperty("size")
	private BigDecimal size;
	@JsonProperty("order_id")
	private String orderId;
	@JsonProperty("created_at")
	private String createdAt;
	@JsonProperty("liquidity")
	private String liquidity;
	@JsonProperty("fee")
	private BigDecimal fee;
	@JsonProperty("settled")
	private Boolean settled;
	@JsonProperty("side")
	private String side;
}
