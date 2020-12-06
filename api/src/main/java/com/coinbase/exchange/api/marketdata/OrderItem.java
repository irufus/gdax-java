package com.coinbase.exchange.api.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class OrderItem implements Comparable<OrderItem> {

	private final BigDecimal price;
	private final BigDecimal size;
	private final String orderId;
	private final BigDecimal num;

	@JsonCreator
	public OrderItem(List<String> limitOrders) {
		if (CollectionUtils.isEmpty(limitOrders) || limitOrders.size() < 3) {
			throw new IllegalArgumentException("LimitOrders was empty - check connection to the exchange");
		}
		price = new BigDecimal(limitOrders.get(0));
		size = new BigDecimal(limitOrders.get(1));
		if (isString(limitOrders.get(2))) {
			orderId = limitOrders.get(2);
		} else {
			orderId = null;
		}
		num = new BigDecimal(1);
	}

	public boolean isString(String value) {
		boolean isDecimalSeparatorFound = false;

		for (char c : value.substring(1).toCharArray()) {
			if (!Character.isDigit(c)) {
				if (c == '.' && !isDecimalSeparatorFound) {
					isDecimalSeparatorFound = true;
					continue;
				}
				return false;
			}
		}
		return true;
	}

	@Override
	public int compareTo(OrderItem o) {
		return this.getPrice().compareTo(o.getPrice()) * -1;
	}
}
