package com.coinbase.exchange.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by irufus on 7/31/15.
 */
public class NewLimitOrderSingle extends NewOrderSingle {
    private BigDecimal size;
    private BigDecimal price;
    private Boolean post_only;

    public NewLimitOrderSingle() {}

    public NewLimitOrderSingle(BigDecimal size, BigDecimal price, Boolean post_only, String product_id) {
        this.size = size;
        this.price = price;
        this.post_only = post_only;
        super.setProduct_id(product_id);
    }

    public NewLimitOrderSingle(BigDecimal size, BigDecimal price, Boolean post_only,
                               String clientOid,
                               String type,
                               String side,
                               String product_id,
                               String stp,
                               String funds) {
        this.size = size;
        this.price = price;
        this.post_only = post_only;
        setClient_oid(clientOid);
        setType(type);
        setSide(side);
        setProduct_id(product_id);
        setStp(stp);
        setFunds(funds);
    }

    public Boolean getPost_only() {
        return post_only;
    }

    public void setPost_only(Boolean post_only) {
        this.post_only = post_only;
    }

    public BigDecimal getPrice() {
        return price.setScale(8, RoundingMode.HALF_UP);
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSize() {
        return size.setScale(8, RoundingMode.HALF_UP);
    }

    public void setSize(BigDecimal size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "NewLimitOrderSingle{"
                + "size=" + size
                + ", price=" + price
                + ", post_only=" + post_only
                + ", client_oid='" + client_oid + '\''
                + ", type='" + type + '\''
                + ", side='" + side + '\''
                + ", product_id='" + product_id + '\''
                + ", stp='" + stp + '\''
                + ", funds='" + funds + '\''
                + '}';
    }
}
