package com.coinbase.exchange.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * <pre>
 *     {
 *       "id": "BTC-USD",
 *       "base_currency": "BTC",
 *       "quote_currency": "USD",
 *       "base_min_size": "0.001",
 *       "base_max_size": "280",
 *       "base_increment": "0.00000001",
 *       "quote_increment": "0.01",
 *       "display_name": "BTC/USD",
 *       "status": "online",
 *       "margin_enabled": false,
 *       "status_message": "",
 *       "min_market_funds": "5",
 *       "max_market_funds": "1000000",
 *       "post_only": false,
 *       "limit_only": false,
 *       "cancel_only": false,
 *       "type": "spot"
 *     }
 * </pre>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
	@JsonProperty("id")
	private String id;
	@JsonProperty("base_currency")
	private String baseCurrency;
	@JsonProperty("quote_currency")
	private String quoteCurrency;
	@JsonProperty("base_min_size")
	private Double baseMinSize;
	@JsonProperty("base_max_size")
	private Double baseMaxSize;
	@JsonProperty("quote_increment")
	private Double quoteIncrement;
	@JsonProperty("base_increment")
	private double baseIncrement;
	@JsonProperty("display_name")
	private String displayName;
	@JsonProperty("status")
	private String status;
	@JsonProperty("margin_enabled")
	private Boolean marginEnabled;
	@JsonProperty("status_message")
	private String statusMessage;
	@JsonProperty("min_market_funds")
	private BigDecimal minMarketFunds;
	@JsonProperty("max_market_funds")
	private Integer maxMarketFunds;
	@JsonProperty("post_only")
	private Boolean postOnly;
	@JsonProperty("limit_only")
	private Boolean limitOnly;
	@JsonProperty("cancel_only")
	private Boolean cancelOnly;
	@JsonProperty("type")
	private String type;
}
