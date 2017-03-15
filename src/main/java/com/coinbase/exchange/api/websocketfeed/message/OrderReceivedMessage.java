package com.coinbase.exchange.api.websocketfeed.message;

import java.math.BigDecimal;

/**
 * A valid order has been received and is now active. This message is
 * emitted for every single valid order as soon as the matching engine
 * receives it whether it fills immediately or not.
 *
 * The received message does not indicate a resting order on the order book.
 * It simply indicates a new incoming order which as been accepted by the
 * matching engine for processing. Received orders may cause match message
 * to follow if they are able to begin being filled (taker behavior).
 * Self-trade prevention may also trigger change messages to follow if the
 * order size needs to be adjusted. Orders which are not fully filled or
 * canceled due to self-trade prevention result in an open message and
 * become resting orders on the order book.
 *
 * Market orders (indicated by the order_type field) may have an optional
 * funds field which indicates how much quote currency will be used to buy
 * or sell. For example, a funds field of 100.00 for the BTC-USD product
 * would indicate a purchase of up to 100.00 USD worth of bitcoin.
 *
 * {
     "type": "received",
     "time": "2014-11-07T08:19:27.028459Z",
     "product_id": "BTC-USD",
     "sequence": 10,
     "order_id": "d50ec984-77a8-460a-b958-66f114b0de9b",
     "size": "1.34",
     "price": "502.1",
     "side": "buy",
     "order_type": "limit"
   }

   {
     "type": "received",
     "time": "2014-11-09T08:19:27.028459Z",
     "product_id": "BTC-USD",
     "sequence": 12,
     "order_id": "dddec984-77a8-460a-b958-66f114b0de9b",
     "funds": "3000.234",
     "side": "buy",
     "order_type": "market"
   }
 *
 * Created by robevansuk on 13/03/2017.
 */
public class OrderReceivedMessage extends Message {


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
    
    public boolean isMarketOrder() {
        return order_type.equals("market");
    }
}
