package com.coinbase.exchange.websocketfeed;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;

/**
 * Generic message that will be passed as an argument to other message types
 * so the relevant parts can be determined and the messages typed.
 * Created by robevansuk on 15/03/2017.
 */
public class OrderBookMessage extends FeedMessage implements Comparable<OrderBookMessage> {

    String trade_id;
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

    String message;
    String open_24h;
    String volume_24h;
    String low_24h;
    String high_24h;
    String volume_30d;
    String best_bid;
    String best_ask;
    String last_size;
    Channel[] channels;

    public OrderBookMessage() {

    }

    public OrderBookMessage(String type, String time, String product_id,
                            String trade_id, Long sequence, String side,
                            String order_id, String order_type, BigDecimal funds,
                            BigDecimal size, BigDecimal price, BigDecimal new_size,
                            BigDecimal old_size, BigDecimal new_funds,
                            BigDecimal old_funds, String reason,
                            BigDecimal remaining_size, String maker_order_id,
                            String taker_order_id, String taker_user_id, String user_id,
                            String taker_profile_id, String profile_id, String last_trade_id,
                            String client_oid, String stp,
                            String message,
                            String open_24h, String volume_24h, String low_24h,
                            String high_24h, String volume_30d, String best_bid,
                            String best_ask, String last_size, Channel[] channels) {
        setType(type);
        setTime(Instant.parse(time));
        setProduct_id(product_id);
        setSequence(sequence);
        this.trade_id = trade_id;
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
        this.message = message;
        this.open_24h = open_24h;
        this.volume_24h = volume_24h;
        this.low_24h = low_24h;
        this.high_24h = high_24h;
        this.volume_30d = volume_30d;
        this.best_bid = best_bid;
        this.best_ask = best_ask;
        this.last_size = last_size;
        this.channels = channels;
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

    public String getTrade_id() {
        return trade_id;
    }

    public void setTrade_id(String trade_id) {
        this.trade_id = trade_id;
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
        return price.setScale(8, RoundingMode.HALF_UP);
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOpen_24h() {
        return open_24h;
    }

    public void setOpen_24h(String open_24h) {
        this.open_24h = open_24h;
    }

    public String getVolume_24h() {
        return volume_24h;
    }

    public void setVolume_24h(String volume_24h) {
        this.volume_24h = volume_24h;
    }

    public String getLow_24h() {
        return low_24h;
    }

    public void setLow_24h(String low_24h) {
        this.low_24h = low_24h;
    }

    public String getHigh_24h() {
        return high_24h;
    }

    public void setHigh_24h(String high_24h) {
        this.high_24h = high_24h;
    }

    public String getVolume_30d() {
        return volume_30d;
    }

    public void setVolume_30d(String volume_30d) {
        this.volume_30d = volume_30d;
    }

    public String getBest_bid() {
        return best_bid;
    }

    public void setBest_bid(String best_bid) {
        this.best_bid = best_bid;
    }

    public String getBest_ask() {
        return best_ask;
    }

    public void setBest_ask(String best_ask) {
        this.best_ask = best_ask;
    }

    public String getLast_size() {
        return last_size;
    }

    public void setLast_size(String last_size) {
        this.last_size = last_size;
    }

    public Channel[] getChannels() {
        return channels;
    }

    public void setChannels(Channel[] channels) {
        this.channels = channels;
    }

    @Override
    public int compareTo(OrderBookMessage other) {
        return this.getSequence().compareTo(other.getSequence());
    }

    @Override
    public String toString() {
        return "OrderBookMessage{" +
                "side='" + side + '\'' +
                ", type='" + getType() + '\'' +
                ", size=" + size +
                ", price=" + getPrice() +
                ", remaining_size=" + remaining_size +
                ", sequence=" + getSequence() +
                '}';
    }

    public static class OrderBookMessageBuilder {
        private final OrderBookMessage message = new OrderBookMessage();

        public OrderBookMessageBuilder setType(String type) {
            message.setType(type);
            return this;
        }
        public OrderBookMessageBuilder setSide(String side) {
            message.setSide(side);
            return this;
        }

        public OrderBookMessageBuilder setPrice(BigDecimal price) {
            message.setPrice(price);
            return this;
        }

        public OrderBookMessageBuilder setSize(BigDecimal size) {
            message.setSize(size);
            return this;
        }

        public OrderBookMessageBuilder setRemainingSize(BigDecimal remaininSize) {
            message.setRemaining_size(remaininSize);
            return this;
        }

        public OrderBookMessageBuilder setSequence(Long id) {
            message.setSequence(id);
            return this;
        }

        public OrderBookMessage build() {
            return message;
        }
    }
}
