package com.coinbase.exchange.api.accounts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @JsonProperty("id")
    private String id;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("balance")
    private BigDecimal balance;
    @JsonProperty("available")
    private BigDecimal available;
    @JsonProperty("hold")
    private BigDecimal hold;
    @JsonProperty("profileId")
    private String profileId;
}
