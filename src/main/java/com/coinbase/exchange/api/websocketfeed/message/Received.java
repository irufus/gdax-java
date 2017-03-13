package com.coinbase.exchange.api.websocketfeed.message;

import org.joda.time.DateTime;

import java.math.BigDecimal;

/**
 * Created by robevansuk on 13/03/2017.
 */
public class Received {

    String type;
    DateTime time;
    String product_id;
    String trade_id;
    Long sequence;
    String order_id;
    BigDecimal funds;
    BigDecimal size;
    BigDecimal price;
    BigDecimal remaining_size;
    String reason;
    String side;
    String order_type;
    String maker_order_id;
    String take_order_id;
    BigDecimal new_size;
    BigDecimal old_size;
    BigDecimal new_funds;
    BigDecimal old_funds;

    public Received() { }

    public Received(String type, DateTime time, String product_id, String trade_id, Long sequence, String order_id, BigDecimal funds, BigDecimal size, BigDecimal price, BigDecimal remaining_size, String reason, String side, String order_type, String maker_order_id, String take_order_id, BigDecimal new_size, BigDecimal old_size, BigDecimal new_funds, BigDecimal old_funds) {
        this.type = type;
        this.time = time;
        this.product_id = product_id;
        this.trade_id = trade_id;
        this.sequence = sequence;
        this.order_id = order_id;
        this.funds = funds;
        this.size = size;
        this.price = price;
        this.remaining_size = remaining_size;
        this.reason = reason;
        this.side = side;
        this.order_type = order_type;
        this.maker_order_id = maker_order_id;
        this.take_order_id = take_order_id;
        this.new_size = new_size;
        this.old_size = old_size;
        this.new_funds = new_funds;
        this.old_funds = old_funds;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DateTime getTime() {
        return time;
    }

    public void setTime(DateTime time) {
        this.time = time;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getTrade_id() {
        return trade_id;
    }

    public void setTrade_id(String trade_id) {
        this.trade_id = trade_id;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public BigDecimal getFunds() {
        return funds;
    }

    public void setFunds(BigDecimal funds) {
        this.funds = funds;
    }

    public BigDecimal getSize() {
        return size;
    }

    public void setSize(BigDecimal size) {
        this.size = size;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getRemaining_size() {
        return remaining_size;
    }

    public void setRemaining_size(BigDecimal remaining_size) {
        this.remaining_size = remaining_size;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getMaker_order_id() {
        return maker_order_id;
    }

    public void setMaker_order_id(String maker_order_id) {
        this.maker_order_id = maker_order_id;
    }

    public String getTake_order_id() {
        return take_order_id;
    }

    public void setTake_order_id(String take_order_id) {
        this.take_order_id = take_order_id;
    }

    public BigDecimal getNew_size() {
        return new_size;
    }

    public void setNew_size(BigDecimal new_size) {
        this.new_size = new_size;
    }

    public BigDecimal getOld_size() {
        return old_size;
    }

    public void setOld_size(BigDecimal old_size) {
        this.old_size = old_size;
    }

    public BigDecimal getNew_funds() {
        return new_funds;
    }

    public void setNew_funds(BigDecimal new_funds) {
        this.new_funds = new_funds;
    }

    public BigDecimal getOld_funds() {
        return old_funds;
    }

    public void setOld_funds(BigDecimal old_funds) {
        this.old_funds = old_funds;
    }
}
