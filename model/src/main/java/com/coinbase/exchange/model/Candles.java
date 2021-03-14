package com.coinbase.exchange.model;

import java.util.List;
import java.util.stream.Collectors;

public class Candles {

    List<Candle> candleList;

    public Candles(List<String[]> candles) {
        this.candleList = candles.stream().map(Candle::new).collect(Collectors.toList());
    }

    public List<Candle> getCandleList() {
        return candleList;
    }

    @Override
    public String toString() {
        return "Candles{"
                + "candleList=" + candleList
                + '}';
    }
}
