package com.coinbase.exchange.api.products;

import com.coinbase.exchange.api.exchange.CoinbaseExchange;
import com.coinbase.exchange.model.Candles;
import com.coinbase.exchange.model.Granularity;
import com.coinbase.exchange.model.Product;
import lombok.Data;
import org.springframework.core.ParameterizedTypeReference;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.joining;

@Data
public class ProductService {

	public static final String PRODUCTS_ENDPOINT = "/products";

	private final CoinbaseExchange exchange;

	// no paged products necessary
	public List<Product> getProducts() {
		return exchange.getAsList(PRODUCTS_ENDPOINT, new ParameterizedTypeReference<>() {
		});
	}

	public Candles getCandles(String productId) {
		return new Candles(exchange.get(PRODUCTS_ENDPOINT + "/" + productId + "/candles", new ParameterizedTypeReference<>() {
		}));
	}

	public Candles getCandles(String productId, Map<String, String> queryParams) {
		StringBuilder url = new StringBuilder(PRODUCTS_ENDPOINT + "/" + productId + "/candles");
		if (queryParams != null && queryParams.size() != 0) {
			url.append("?");
			url.append(queryParams.entrySet().stream()
					.map(entry -> entry.getKey() + "=" + entry.getValue())
					.collect(joining("&")));
		}
		return new Candles(exchange.get(url.toString(), new ParameterizedTypeReference<>() {
		}));
	}

	/**
	 * If either one of the start or end fields are not provided then both fields will be ignored.
	 * If a custom time range is not declared then one ending now is selected.
	 */
	public Candles getCandles(String productId, Instant startTime, Instant endTime, Granularity granularity) {

		Map<String, String> queryParams = new HashMap<>();

		if (startTime != null) {
			queryParams.put("start", startTime.toString());
		}
		if (endTime != null) {
			queryParams.put("end", endTime.toString());
		}
		if (granularity != null) {
			queryParams.put("granularity", granularity.get());
		}

		return getCandles(productId, queryParams);
	}

	/**
	 * The granularity field must be one of the following values: {60, 300, 900, 3600, 21600, 86400}
	 */
	public Candles getCandles(String productId, Granularity granularity) {
		return getCandles(productId, null, null, granularity);
	}

	/**
	 * If either one of the start or end fields are not provided then both fields will be ignored.
	 */
	public Candles getCandles(String productId, Instant start, Instant end) {
		return getCandles(productId, start, end, null);
	}
}
