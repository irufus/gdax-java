package com.coinbase.exchange.api.marketdata;

import com.coinbase.exchange.api.exchange.CoinbaseExchange;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

@Data
@AllArgsConstructor
public class MarketDataService {

	public static final String PRODUCT_ENDPOINT = "/products";
	private final CoinbaseExchange exchange;

	public MarketData getMarketDataOrderBook(String productId, int level) {
		String marketDataEndpoint = PRODUCT_ENDPOINT + "/" + productId + "/book";
		if (level != 1)
			marketDataEndpoint += "?level=" + level;
		return exchange.get(marketDataEndpoint, new ParameterizedTypeReference<>() {
		});
	}

	public List<Trade> getTrades(String productId) {
		String tradesEndpoint = PRODUCT_ENDPOINT + "/" + productId + "/trades";
		return exchange.getAsList(tradesEndpoint, new ParameterizedTypeReference<>() {
		});
	}
}
