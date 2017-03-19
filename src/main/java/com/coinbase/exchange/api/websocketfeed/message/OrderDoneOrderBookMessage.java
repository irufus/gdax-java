package com.coinbase.exchange.api.websocketfeed.message;

/**
 * {
     "type": "done",
     "time": "2014-11-07T08:19:27.028459Z",
     "product_id": "BTC-USD",
     "sequence": 10,
     "price": "200.2",
     "order_id": "d50ec984-77a8-460a-b958-66f114b0de9b",
     "reason": "filled", // canceled
     "side": "sell",
     "remaining_size": "0.2"
     }
 * Created by robevansuk on 15/03/2017.
 */
public class OrderDoneOrderBookMessage extends OrderBookMessage {
}
