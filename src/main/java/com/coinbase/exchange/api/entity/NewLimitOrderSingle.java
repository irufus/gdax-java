package com.coinbase.exchange.api.entity;

import java.math.BigDecimal;

/**
 * Created by irufus on 7/31/15.
 */
public class NewLimitOrderSingle extends NewOrderSingle {
    private BigDecimal size;
    private BigDecimal price;
    private Boolean post_only;

    public NewLimitOrderSingle() {}

<<<<<<< HEAD
    public NewLimitOrderSingle(BigDecimal size, BigDecimal price, Boolean post_only, String productId) {
        this.size = size;
        this.price = price;
        this.post_only = post_only;
        this.product_id = productId;
=======
    public NewLimitOrderSingle(BigDecimal size, BigDecimal price, Boolean post_only) {
        this.size = size;
        this.price = price;
        this.post_only = post_only;
>>>>>>> parent of 576452b... These are the changes I made to run your source as part of our microservice.
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
        return price.setScale(8, BigDecimal.ROUND_HALF_UP);
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSize() {
        return size.setScale(8, BigDecimal.ROUND_HALF_UP);
    }

    public void setSize(BigDecimal size) {
        this.size = size;
    }
}
