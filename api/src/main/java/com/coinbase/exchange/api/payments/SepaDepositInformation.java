package com.coinbase.exchange.api.payments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SepaDepositInformation {
	@JsonProperty("iban")
	private String iban;
	@JsonProperty("swift")
	private String swift;
	@JsonProperty("bank_name")
	private String bankName;
	@JsonProperty("bank_address")
	private String bankAddress;
	@JsonProperty("bank_country_name")
	private String bankCountryName;
	@JsonProperty("account_name")
	private String accountName;
	@JsonProperty("account_address")
	private String accountAddress;
	@JsonProperty("reference")
	private String reference;
}
