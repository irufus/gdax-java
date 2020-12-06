package com.coinbase.exchange.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NewMarketOrderSingle extends NewOrderSingle {

	private BigDecimal size; //optional: Desired amount in BTC

	public NewMarketOrderSingle() {
		super.setType("market");
	}
}
