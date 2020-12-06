package com.coinbase.exchange.api.payments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountLimit {
	@JsonProperty("period_in_days")
	private Integer periodInDays;
	@JsonProperty("total")
	private Amount total;
	@JsonProperty("remaining")
	private Amount remaining;
}
