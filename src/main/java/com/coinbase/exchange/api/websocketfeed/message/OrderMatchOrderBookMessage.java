package com.coinbase.exchange.api.websocketfeed.message;

/**
 * A trade occurred between two orders. The aggressor or taker order is
 * the one executing immediately after being received and the maker order
 * is a resting order on the book. The side field indicates the maker
 * order side. If the side is sell this indicates the maker was a sell
 * order and the match is considered an up-tick. A buy side match is a
 * down-tick.
 {
     "type": "match",
     "trade_id": 10,
     "sequence": 50,
     "maker_order_id": "ac928c66-ca53-498f-9c13-a110027a60e8",
     "taker_order_id": "132fb6ae-456b-4654-b4e0-d681ac05cea1",
     "time": "2014-11-07T08:19:27.028459Z",
     "product_id": "BTC-USD",
     "size": "5.23512",
     "price": "400.23",
     "side": "sell"
 }

 If authenticated, and you were the taker, the message would also have
 the following fields:

     taker_user_id: "5844eceecf7e803e259d0365",
     user_id: "5844eceecf7e803e259d0365",
     taker_profile_id: "765d1549-9660-4be2-97d4-fa2d65fa3352",
     profile_id: "765d1549-9660-4be2-97d4-fa2d65fa3352"

 * Created by robevansuk on 15/03/2017.
 */
public class OrderMatchOrderBookMessage extends OrderBookMessage {
}
