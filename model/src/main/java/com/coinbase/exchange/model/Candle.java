package com.coinbase.exchange.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Candle {
	private Instant time;
	private BigDecimal low;
	private BigDecimal high;
	private BigDecimal open;
	private BigDecimal close;
	private BigDecimal volume;

	public Candle(String[] entry) {
		this(Instant.ofEpochSecond(Long.parseLong(entry[0])),
				new BigDecimal(entry[1]),
				new BigDecimal(entry[2]),
				new BigDecimal(entry[3]),
				new BigDecimal(entry[4]),
				new BigDecimal(entry[5]));
	}
}
