package com.coinbase.exchange.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hold {
	@JsonProperty("id")
    String id;
	@JsonProperty("account_id")
    String accountId;
	@JsonProperty("created_at")
    String createdAt;
	@JsonProperty("update_at")
    String updateAt;
	@JsonProperty("amount")
    BigDecimal amount;
	@JsonProperty("type")
    String type;
	@JsonProperty("ref")
    String ref;
}
