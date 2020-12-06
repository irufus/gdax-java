package com.coinbase.exchange.api.useraccount;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountData {
    @JsonProperty("product_id")
    private String productId;
    @JsonProperty("exchange_volume")
    private BigDecimal exchangeVolume;
    @JsonProperty("volume")
    private BigDecimal volume;
    @JsonProperty("recorded_at")
    private String recordedAt;
}
