package com.coinbase.exchange.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CoinbasePaymentRequest extends MonetaryRequest {
    @JsonProperty("coinbase_account_id")
    private String coinbaseAccountId;
    @JsonProperty("payment_method_id")
    private String paymentMethodId;

    public CoinbasePaymentRequest(BigDecimal amount, String currency, String coinbaseAccountId) {
        super(amount, currency);
        this.coinbaseAccountId = coinbaseAccountId;
        this.paymentMethodId = coinbaseAccountId; //Duplicated field for gdax compliance, I believe
        //We could probably remove coinbaseAccountId but there are no tests for this specific thing
    }
}
