package com.coinbase.exchange.api.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class OrderBuilder {
    @JsonProperty("id")
    private String id;
    @JsonProperty("size")
    private String size;
    @JsonProperty("price")
    private String price;
    @JsonProperty("product_id")
    private String productId;
    @JsonProperty("status")
    private String status;
    @JsonProperty("filled_size")
    private String filledSize;
    @JsonProperty("fill_fees")
    private String fillFees;
    @JsonProperty("side")
    private String side;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("settled")
    private Boolean settled;

    public OrderBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public OrderBuilder setSize(String size) {
        this.size = size;
        return this;
    }

    public OrderBuilder setPrice(String price) {
        this.price = price;
        return this;
    }

    public OrderBuilder setProductId(String productId) {
        this.productId = productId;
        return this;
    }

    public OrderBuilder setStatus(String status) {
        this.status = status;
        return this;
    }

    public OrderBuilder setFilledSize(String filledSize) {
        this.filledSize = filledSize;
        return this;
    }

    public OrderBuilder setFillFees(String fillFees) {
        this.fillFees = fillFees;
        return this;
    }

    public OrderBuilder setSettled(Boolean settled) {
        this.settled = settled;
        return this;
    }

    public OrderBuilder setSide(String side) {
        this.side = side;
        return this;
    }

    public OrderBuilder setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Order build() {
        return new Order(this);
    }
}
