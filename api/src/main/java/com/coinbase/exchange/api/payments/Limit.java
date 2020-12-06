package com.coinbase.exchange.api.payments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Limit {
    @JsonProperty("buy")
    private AccountLimit[] buy;
    @JsonProperty("instant_buy")
    private AccountLimit[] instantBuy;
    @JsonProperty("sell")
    private AccountLimit[] sell;
    @JsonProperty("deposit")
    private AccountLimit[] deposit;
}
