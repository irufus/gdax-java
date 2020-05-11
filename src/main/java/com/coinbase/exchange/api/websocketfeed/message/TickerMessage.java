package com.coinbase.exchange.api.websocketfeed.message;

import java.math.BigDecimal;

/**
 * The ticker channel provides real-time price updates every time a match happens
 * <pre>
 *  {
 *   "type": "ticker",
 *   "trade_id": 8584621,
 *   "time": "2020-04-10T10:51:10.085085Z",
 *   "sequence": 9119799283,
 *   "product_id": "BTC-GBP",
 *   "price": "5587.84",
 *   "open_24h": "5900.24000000",
 *   "volume_24h": "1192.24218333",
 *   "low_24h": "5509.11000000",
 *   "high_24h": "5914.86000000",
 *   "volume_30d": "50369.34116944",
 *   "best_bid": "5581.72",
 *   "best_ask": "5587.84",
 *   "side": "buy",
 *   "last_size": "0.25926371"
 * }
 * </pre>
 */
public class TickerMessage extends FeedMessage {
/*    Object sequence;
    Object product_id;
    Object price;
    Object side;
    Object time;
    Object trade_id;*/

    BigDecimal open_24h;
    BigDecimal volume_24h;
    BigDecimal low_24h;
    BigDecimal high_24h;
    BigDecimal volume_30d;
    BigDecimal best_bid;
    BigDecimal best_ask;
    BigDecimal last_size;
}
