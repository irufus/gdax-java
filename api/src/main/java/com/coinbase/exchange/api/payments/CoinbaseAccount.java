package com.coinbase.exchange.api.payments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoinbaseAccount {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("balance")
    private BigDecimal balance;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("type")
    private String type;
    @JsonProperty("primary")
    private Boolean primary;
    @JsonProperty("active")
    private Boolean active;
    @JsonProperty("wire_deposit_information")
    private DepositInformation wireDepositInformation;
    @JsonProperty("sepa_deposit_information")
    private SepaDepositInformation sepaDepositInformation;
}
