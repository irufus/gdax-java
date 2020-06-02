package com.coinbase.exchange.websocketfeed;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * The ticker channel provides real-time price updates every time a match happens
 * <pre>
 * {
 *     "type": "ticker",
 *     "trade_id": 20153558,
 *     "sequence": 3262786978,
 *     "time": "2017-09-02T17:05:49.250000Z",
 *     "product_id": "BTC-USD",
 *     "price": "4388.01000000",
 *     "side": "buy", // Taker side
 *     "last_size": "0.03000000",
 *     "best_bid": "4388",
 *     "best_ask": "4388.01"
 * }
 * </pre>
 */
public class TickerMessage extends FeedMessage {

    private Long trade_id;
    private Long sequence;
    private Instant time;
    private String product_id;
    private BigDecimal price;
    private String side;
    private BigDecimal last_size;
    private BigDecimal best_bid;
    private BigDecimal best_ask;

    public TickerMessage() {
        setType("ticker");
    }

    public TickerMessage(Long trade_id,
                         Long sequence,
                         Instant time,
                         String product_id,
                         BigDecimal price,
                         String side,
                         BigDecimal last_size,
                         BigDecimal best_bid,
                         BigDecimal best_ask) {
        this();
        this.trade_id = trade_id;
        this.sequence = sequence;
        this.time = time;
        this.product_id = product_id;
        this.price = price;
        this.side = side;
        this.last_size = last_size;
        this.best_bid = best_bid;
        this.best_ask = best_ask;
    }

    public Long getTrade_id() {
        return trade_id;
    }

    public void setTrade_id(Long trade_id) {
        this.trade_id = trade_id;
    }

    @Override
    public Long getSequence() {
        return sequence;
    }

    @Override
    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    @Override
    public Instant getTime() {
        return time;
    }

    @Override
    public void setTime(Instant time) {
        this.time = time;
    }

    @Override
    public String getProduct_id() {
        return product_id;
    }

    @Override
    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public BigDecimal getLast_size() {
        return last_size;
    }

    public void setLast_size(BigDecimal last_size) {
        this.last_size = last_size;
    }

    public BigDecimal getBest_bid() {
        return best_bid;
    }

    public void setBest_bid(BigDecimal best_bid) {
        this.best_bid = best_bid;
    }

    public BigDecimal getBest_ask() {
        return best_ask;
    }

    public void setBest_ask(BigDecimal best_ask) {
        this.best_ask = best_ask;
    }
}
