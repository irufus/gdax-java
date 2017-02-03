package com.coinbase.exchange.api.orders;

import java.math.BigDecimal;

/**
 * Created by robevansuk on 03/02/2017.
 */
public class Order {
    String id;
    BigDecimal size;
    BigDecimal price;
    String product_id;
    String status;
    BigDecimal filled_size;
    BigDecimal fill_fees;
    Boolean settled;
    String side;
    String created_at;

    public Order(OrderBuilder builder) {
        this.id = builder.getId();
        this.size = builder.getSize();
        this.price = builder.getPrice();
        this.product_id = builder.getProduct_id();
        this.status = builder.getStatus();
        this.filled_size = builder.getFilled_size();
        this.fill_fees = builder.getFill_fees();
        this.settled = builder.getSettled();
        this.side = builder.getSide();
        this.created_at = builder.getCreated_at();
    }
}
