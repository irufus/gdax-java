package com.coinbase.exchange.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CryptoPaymentRequest extends MonetaryRequest {
	@JsonProperty("crypto_address")
	private String cryptoAddress;

	public CryptoPaymentRequest(BigDecimal amount, String currency, String cryptoAddress) {
		super(amount, currency);
		this.cryptoAddress = cryptoAddress;
	}
}
