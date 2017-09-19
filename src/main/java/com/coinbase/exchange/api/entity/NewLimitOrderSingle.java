package com.coinbase.exchange.api.entity;

import java.math.BigDecimal;

/**
 * Created by irufus on 7/31/15.
 */
public class NewLimitOrderSingle extends NewOrderSingle {
    private String size;
    private String price;
    private Boolean post_only;

    public NewLimitOrderSingle() {}

    public NewLimitOrderSingle(BigDecimal size, BigDecimal price, Boolean post_only) {
        setSize(size);
        setPrice(price);
        this.post_only = post_only;
    }

    public NewLimitOrderSingle(BigDecimal size, BigDecimal price, Boolean post_only,
                               String type,
                               String side,
                               String product_id,
                               String stp) {
        setSize(size);
        setPrice(price);
        this.post_only = post_only;
        setType(type);
        setSide(side);
        setProduct_id(product_id);
        setStp(stp);
    }

    public Boolean getPost_only() {
        return post_only;
    }

    public void setPost_only(Boolean post_only) {
        this.post_only = post_only;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price == null ? null : price.setScale(8, BigDecimal.ROUND_HALF_UP).toString();
    }

    public String getSize() {
        return size;
    }

    public void setSize(BigDecimal size) {
        this.size = size == null ? null : size.setScale(8, BigDecimal.ROUND_HALF_UP).toString();
    }
}
