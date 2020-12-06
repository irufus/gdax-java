package com.coinbase.exchange.api.marketdata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketData {

    private Long sequence;
    private List<OrderItem> bids; // price, size, orders
    private List<OrderItem> asks; // price, size, orders
}
