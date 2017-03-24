package com.coinbase.exchange.api.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by robevansuk on 20/03/2017.
 */
public class OrderItem {

    private BigDecimal price;
    private BigDecimal size;
    private BigDecimal num;

    @JsonCreator
    public OrderItem(List<BigDecimal> limitOrders) {
        if (CollectionUtils.isEmpty(limitOrders) || limitOrders.size() < 3) {
            throw new IllegalArgumentException("dkfjdkfjdlkjf");
        }

        price = limitOrders.get(0);
        size = limitOrders.get(1);
        num = limitOrders.get(2);
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getSize() {
        return size;
    }

    public BigDecimal getNum() {
        return num;
    }

}
