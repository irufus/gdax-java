package com.coinbase.exchange.api.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;

/**
 * Created by robevansuk on 20/03/2017.
 */
public class OrderItem implements Comparable {

    private BigDecimal price;
    private BigDecimal size;
    private String orderId;
    private BigDecimal num;


    @JsonCreator
    public OrderItem(List<String> limitOrders) {
        if (CollectionUtils.isEmpty(limitOrders) || limitOrders.size() < 3) {
            throw new IllegalArgumentException("LimitOrders was empty - check connection to the exchange");
        }
        price =  new BigDecimal(limitOrders.get(0));
        size = new BigDecimal(limitOrders.get(1));
        if (isString(limitOrders.get(2))) {
            orderId = limitOrders.get(2);
            num = new BigDecimal(1);
        } else {
            num = new BigDecimal(1);
        }
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getSize() {
        return size;
    }

    public String getOrderId() {
        return orderId;
    }

    public BigDecimal getNum() {
        return num;
    }

    @Override
    public int compareTo(Object o) {
        return this.getPrice().compareTo(((OrderItem)o).getPrice()) * -1;
    }

    public boolean isString(String value) {
        boolean isDecimalSeparatorFound = false;

        for (char c : value.substring( 1 ).toCharArray()) {
            if (!Character.isDigit( c ) ) {
                if (c == '.' && !isDecimalSeparatorFound) {
                    isDecimalSeparatorFound = true;
                    continue;
                }
                return false;
            }
        }
        return true;
    }
}
