package com.coinbase.exchange.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class NewLimitOrderSingle extends NewOrderSingle {
    @JsonProperty("size")
    private BigDecimal size;
    @JsonProperty("price")
    private BigDecimal price;
    @JsonProperty("post_only")
    private Boolean postOnly;

    public NewLimitOrderSingle() {}

    public NewLimitOrderSingle(BigDecimal size, BigDecimal price, Boolean postOnly, String productId) {
        this.size = size;
        this.price = price;
        this.postOnly = postOnly;
        super.setProductId(productId);
    }

    public NewLimitOrderSingle(BigDecimal size, BigDecimal price, Boolean postOnly,
                               String clientOid,
                               String type,
                               String side,
                               String productId,
                               String stp,
                               String funds) {
        this.size = size;
        this.price = price;
        this.postOnly = postOnly;
        setClientOid(clientOid);
        setType(type);
        setSide(side);
        setProductId(productId);
        setStp(stp);
        setFunds(funds);
    }
}
