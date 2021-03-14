package com.coinbase.exchange.model;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Created by irufus on 2/18/15.
 */
public class Fill {
    private Integer trade_id;
    private String product_id;
    private BigDecimal price;
    private BigDecimal size;
    private String order_id;
    private Instant created_at;
    private String liquidity;
    private BigDecimal fee;
    private Boolean settled;
    private String side;

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public Boolean getSettled() {
        return settled;
    }

    public void setSettled(Boolean settled) {
        this.settled = settled;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public Instant getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Instant created_at) {
        this.created_at = created_at;
    }

    public String getLiquidity() {
        return liquidity;
    }

    public void setLiquidity(String liquidity) {
        this.liquidity = liquidity;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public BigDecimal getSize() {
        return size;
    }

    public void setSize(BigDecimal size) {
        this.size = size;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public Integer getTrade_id() {
        return trade_id;
    }

    public void setTrade_id(Integer trade_id) {
        this.trade_id = trade_id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Fill{"
                + "trade_id=" + trade_id
                + ", product_id='" + product_id + '\''
                + ", price=" + price
                + ", size=" + size
                + ", order_id='" + order_id + '\''
                + ", created_at=" + created_at
                + ", liquidity='" + liquidity + '\''
                + ", fee=" + fee
                + ", settled=" + settled
                + ", side='" + side + '\''
                + '}';
    }
}
