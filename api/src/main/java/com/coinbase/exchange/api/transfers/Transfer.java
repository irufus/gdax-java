package com.coinbase.exchange.api.transfers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transfer {
	@JsonProperty("type")
	private String type; // deposit/withdraw
	@JsonProperty("amount")
	private BigDecimal amount;
	@JsonProperty("coinbase_account_id")
	private String coinbaseAccountId;
}
