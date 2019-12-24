package com.coinbase.exchange.api.websocketfeed.message;

import java.math.BigDecimal;

/**
 * Generic message that will be passed as an argument to other message types
 * so the relevant parts can be determined and the messages typed.
 * Created by robevansuk on 15/03/2017.
 */
public class OrderBookMessage implements Comparable {
    String type;
    String time;
    String product_id;
    String trade_id;
    Long sequence;
    String side;
    String order_id;
    String order_type;

    BigDecimal funds;

    BigDecimal size;
    BigDecimal price;

    BigDecimal new_size;
    BigDecimal old_size;
    BigDecimal new_funds;
    BigDecimal old_funds;

    String reason;
    BigDecimal remaining_size;

    String maker_order_id;
    String taker_order_id;
    String taker_user_id;
    String user_id;
    String taker_profile_id;
    String profile_id;

    String last_trade_id;

    String client_oid;
    String stp;

    public OrderBookMessage() { }

    public OrderBookMessage(String type, String time, String product_id,
                            String trade_id, Long sequence, String side,
                            String order_id, String order_type, BigDecimal funds,
                            BigDecimal size, BigDecimal price, BigDecimal new_size,
                            BigDecimal old_size, BigDecimal new_funds,
                            BigDecimal old_funds, String reason,
                            BigDecimal remaining_size, String maker_order_id,
                            String taker_order_id, String taker_user_id, String user_id,
                            String taker_profile_id, String profile_id, String last_trade_id,
                            String client_oid, String stp) {
        this.type = type;
        this.time = time;
        this.product_id = product_id;
        this.trade_id = trade_id;
        this.sequence = sequence;
        this.side = side;
        this.order_id = order_id;
        this.order_type = order_type;
        this.funds = funds;
        this.size = size;
        this.price = price;
        this.new_size = new_size;
        this.old_size = old_size;
        this.new_funds = new_funds;
        this.old_funds = old_funds;
        this.reason = reason;
        this.remaining_size = remaining_size;
        this.maker_order_id = maker_order_id;
        this.taker_order_id = taker_order_id;
        this.taker_user_id = taker_user_id;
        this.user_id = user_id;
        this.taker_profile_id = taker_profile_id;
        this.profile_id = profile_id;
        this.last_trade_id = last_trade_id;
        this.client_oid = client_oid;
        this.stp = stp;

    }

    public String getClient_oid() {
        return client_oid;
    }

    public void setClient_oid(String client_oid) {
        this.client_oid = client_oid;
    }

    public String getStp() {
        return stp;
    }

    public void setStp(String stp) {
        this.stp = stp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
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

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public BigDecimal getRemaining_size() {
        return remaining_size;
    }

    public void setRemaining_size(BigDecimal remaining_size) {
        this.remaining_size = remaining_size;
    }

    public String getMaker_order_id() {
        return maker_order_id;
    }

    public void setMaker_order_id(String maker_order_id) {
        this.maker_order_id = maker_order_id;
    }

    public String getTaker_order_id() {
        return taker_order_id;
    }

    public void setTaker_order_id(String taker_order_id) {
        this.taker_order_id = taker_order_id;
    }

    public String getTaker_user_id() {
        return taker_user_id;
    }

    public void setTaker_user_id(String taker_user_id) {
        this.taker_user_id = taker_user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTaker_profile_id() {
        return taker_profile_id;
    }

    public void setTaker_profile_id(String taker_profile_id) {
        this.taker_profile_id = taker_profile_id;
    }

    public String getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
    }

    public String getLast_trade_id() {
        return last_trade_id;
    }

    public void setLast_trade_id(String last_trade_id) {
        this.last_trade_id = last_trade_id;
    }

    @Override
    public int compareTo(Object o) {
        return this.getSequence().compareTo(((OrderBookMessage) o).getSequence());
//        if (getSide().equals("buy")) {
//            return result;
//        } else {
//            return result * -1;
//        }
    }
}
