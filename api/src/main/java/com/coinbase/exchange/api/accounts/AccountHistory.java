package com.coinbase.exchange.api.accounts;

import com.coinbase.exchange.model.Detail;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountHistory {
	@JsonProperty("id")
	private Integer id;
	@JsonProperty("created_at")
	private String createdAt;
	@JsonProperty("amount")
	private BigDecimal amount;
	@JsonProperty("balance")
	private BigDecimal balance;
	@JsonProperty("type")
	private String type;
	@JsonProperty("detail")
	private Detail detail;
}
