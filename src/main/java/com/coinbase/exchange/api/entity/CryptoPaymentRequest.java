package com.coinbase.exchange.api.entity;

import java.math.BigDecimal;

public class CryptoPaymentRequest extends MonetaryRequest {
    private String cryptoAddress;

    public CryptoPaymentRequest(BigDecimal amount, String currency, String cryptoAddress) {
        super(amount, currency);
        this.cryptoAddress = cryptoAddress;
    }
    public String getCryptoAddress() {
        return cryptoAddress;
    }
    public void setCryptoAddress(String cryptoAddress) {
        this.cryptoAddress = cryptoAddress;
    }
}
