package com.coinbase.exchange.api.websocketfeed.message;

import org.joda.time.DateTime;

import java.math.BigDecimal;

/**
 * Created by robevansuk on 15/03/2017.
 */
public class Message {
    String type;
    DateTime time;
    String product_id;
    String trade_id;
    Long sequence;
    String side;
    String order_id;
    String order_type;

    BigDecimal funds;

    BigDecimal size;
    BigDecimal price;


}
