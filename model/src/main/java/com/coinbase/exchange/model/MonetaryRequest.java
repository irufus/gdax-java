package com.coinbase.exchange.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class MonetaryRequest {
    protected BigDecimal amount;
    protected String currency;
}
