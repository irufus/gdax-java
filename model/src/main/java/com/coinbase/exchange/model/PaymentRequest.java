package com.coinbase.exchange.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PaymentRequest extends MonetaryRequest {

    @JsonProperty("payment_method_id")
    private String paymentMethodId;

    public PaymentRequest(BigDecimal amount, String currency, String paymentMethodId) {
        super(amount, currency);
        this.paymentMethodId = paymentMethodId;
    }
}
