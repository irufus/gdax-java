package com.coinbase.exchange.api.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trade {
	@JsonProperty("time")
	private Instant time;
	@JsonProperty("trade_id")
	private Long tradeId;
	@JsonProperty("price")
	private BigDecimal price;
	@JsonProperty("size")
	private BigDecimal size;
	@JsonProperty("side")
	private String side;
}
