package com.coinbase.exchange.api.orders;

/**
 * Created by irufus on 2/18/15.
 */
public class OrderBuilder {

    private String id;
    private String size;
    private String price;
    private String product_id;
    private String status;
    private String filled_size;
    private String fill_fees;
    private String side;
    private String created_at;
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

    public OrderBuilder setProduct_id(String product_id) {
        this.product_id = product_id;
        return this;
    }

    public OrderBuilder setStatus(String status) {
        this.status = status;
        return this;
    }

    public OrderBuilder setFilled_size(String filled_size) {
        this.filled_size = filled_size;
        return this;
    }

    public OrderBuilder setFill_fees(String fill_fees) {
        this.fill_fees = fill_fees;
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

    public OrderBuilder setCreated_at(String created_at) {
        this.created_at = created_at;
        return this;
    }

    public String getId() {
        return id;
    }

    public String getSize() {
        return size;
    }

    public String getPrice() {
        return price;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getStatus() {
        return status;
    }

    public String getFilled_size() {
        return filled_size;
    }

    public String getFill_fees() {
        return fill_fees;
    }

    public Boolean getSettled() {
        return settled;
    }

    public String getSide() {
        return side;
    }

    public String getCreated_at() {
        return created_at;
    }

    public Order build() {
        return new Order(this);
    }
}
