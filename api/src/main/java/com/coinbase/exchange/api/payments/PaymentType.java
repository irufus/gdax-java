package com.coinbase.exchange.api.payments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentType {
    @JsonProperty("id")
    private String id; // UUID
    @JsonProperty("type")
    private String type; // ach_bank_account
    @JsonProperty("name")
    private String name; // Bank of America - eBan... ********7134
    @JsonProperty("currency")
    private String currency; // USD
    @JsonProperty("primary_buy")
    private Boolean primaryBuy;
    @JsonProperty("primary_sell")
    private Boolean primarySell;
    @JsonProperty("allow_buy")
    private Boolean allowBuy;
    @JsonProperty("allow_sell")
    private Boolean allowSell;
    @JsonProperty("allow_deposit")
    private Boolean allowDeposit;
    @JsonProperty("allow_withdraw")
    private Boolean allowWithdraw;
    @JsonProperty("limits")
    private Limit limits;
}
