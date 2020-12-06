package com.coinbase.exchange.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Currency {
	@JsonProperty("id")
	private String id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("min_size")
	private BigDecimal minSize;
	@JsonProperty("status")
	private String status;
	@JsonProperty("status_message")
	private String statusMessage;
	@JsonProperty("max_precision")
	private BigDecimal maxPrecision;
	@JsonProperty("convertible_to")
	private String[] convertibleTo;
	@JsonProperty("funding_account_id")
	private String fundingAccountId;
	@JsonProperty("details")
	private Object details;
}
