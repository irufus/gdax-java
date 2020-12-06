package com.coinbase.exchange.websocketfeed;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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
@Data
@EqualsAndHashCode(callSuper = true)
public class TickerMessage extends FeedMessage {

    @JsonProperty("trade_id")
    private Long tradeId;
    @JsonProperty("sequence")
    private Long sequence;
    @JsonProperty("time")
    private Instant time;
    @JsonProperty("product_id")
    private String productId;
    @JsonProperty("price")
    private BigDecimal price;
    @JsonProperty("side")
    private String side;
    @JsonProperty("last_size")
    private BigDecimal lastSize;
    @JsonProperty("best_bid")
    private BigDecimal bestBid;
    @JsonProperty("best_ask")
    private BigDecimal bestAsk;

    public TickerMessage() {
        setType("ticker");
    }

    public TickerMessage(Long tradeId,
                         Long sequence,
                         Instant time,
                         String productId,
                         BigDecimal price,
                         String side,
                         BigDecimal lastSize,
                         BigDecimal bestBid,
                         BigDecimal bestAsk) {
        this();
        this.tradeId = tradeId;
        this.sequence = sequence;
        this.time = time;
        this.productId = productId;
        this.price = price;
        this.side = side;
        this.lastSize = lastSize;
        this.bestBid = bestBid;
        this.bestAsk = bestAsk;
    }
}
