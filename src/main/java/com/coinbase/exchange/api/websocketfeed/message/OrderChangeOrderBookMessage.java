package com.coinbase.exchange.api.websocketfeed.message;

/**
 * An order has changed. This is the result of self-trade prevention
 * adjusting the order size or available funds. Orders can only
 * decrease in size or funds. change messages are sent anytime an
 * order changes in size; this includes resting orders (open) as
 * well as received but not yet open. change messages are also
 * sent when a new market order goes through self trade prevention
 * and the funds for the market order have changed.
 *
 * {
 *     "type": "change",
 *     "time": "2014-11-07T08:19:27.028459Z",
 *     "sequence": 80,
 *     "order_id": "ac928c66-ca53-498f-9c13-a110027a60e8",
 *     "product_id": "BTC-USD",
 *     "new_size": "5.23512",
 *     "old_size": "12.234412",
 *     "price": "400.23",
 *     "side": "sell"
 * }
 *
 * change messages for received but not yet open orders can be
 * ignored when building a real-time order book. The side field
 * of a change message and price can be used as indicators for whether
 * the change message is relevant if building from a level 2 book.
 *
 * Any change message where the price is null indicates that the change
 * message is for a market order. Change messages for limit orders will
 * always have a price specified.
 *
 * {
 *     "type": "change",
 *     "time": "2014-11-07T08:19:27.028459Z",
 *     "sequence": 80,
 *     "order_id": "ac928c66-ca53-498f-9c13-a110027a60e8",
 *     "product_id": "BTC-USD",
 *     "new_funds": "5.23512",
 *     "old_funds": "12.234412",
 *     "price": "400.23",
 *     "side": "sell"
 * }
 *
 * Created by robevansuk on 15/03/2017.
 */
public class OrderChangeOrderBookMessage extends OrderBookMessage {


}
