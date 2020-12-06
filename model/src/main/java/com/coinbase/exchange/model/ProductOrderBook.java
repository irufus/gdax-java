package com.coinbase.exchange.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrderBook {
	@JsonProperty("sequence")
	private Integer sequence;
	@JsonProperty("bids")
	private List<List<String>> bids;
	@JsonProperty("asks")
	private List<List<String>> asks;
}
