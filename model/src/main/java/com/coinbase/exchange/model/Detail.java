package com.coinbase.exchange.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Detail {
    @JsonProperty("order_id")
    private String orderId;
    @JsonProperty("trade_id")
    private Integer tradeId;
    @JsonProperty("product_id")
    private String productId;
}
