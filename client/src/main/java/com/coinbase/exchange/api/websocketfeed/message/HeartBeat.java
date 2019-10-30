package com.coinbase.exchange.api.websocketfeed.message;

/**
 * A message sent once a second when heartbeat is turned on.
 *
 * {
 *     "type": "heartbeat",                   // inherited
 *     "sequence": 90,                        // inherited
 *     "last_trade_id": 20,
 *     "product_id": "BTC-USD",               // inherited
 *     "time": "2014-11-07T08:19:28.464459Z"  // inherited
 * }
 *
 * Created by robevansuk on 19/03/2017.
 */
public class HeartBeat extends OrderBookMessage {

    String last_trade_id;


}
