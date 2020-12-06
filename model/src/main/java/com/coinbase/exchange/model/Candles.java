package com.coinbase.exchange.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class Candles {

	private List<Candle> candleList;

	public Candles(List<String[]> candles) {
		setCandleList(candles.stream().map(Candle::new).collect(Collectors.toList()));
	}
}
