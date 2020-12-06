package com.coinbase.exchange.api.orders;

import com.coinbase.exchange.api.exchange.CoinbaseExchange;
import com.coinbase.exchange.model.Fill;
import com.coinbase.exchange.model.Hold;
import com.coinbase.exchange.model.NewOrderSingle;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.ParameterizedTypeReference;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderService {
	public static final String ORDERS_ENDPOINT = "/orders";
	public static final String FILLS_ENDPOINT = "/fills";

	private final CoinbaseExchange exchange;

	public List<Hold> getHolds(String accountId) {
		return exchange.getAsList(ORDERS_ENDPOINT + "/" + accountId + "/holds", new ParameterizedTypeReference<>() {
		});
	}

	public List<Order> getOpenOrders(String accountId) {
		return exchange.getAsList(ORDERS_ENDPOINT + "/" + accountId + "/orders", new ParameterizedTypeReference<>() {
		});
	}

	public Order getOrder(String orderId) {
		return exchange.get(ORDERS_ENDPOINT + "/" + orderId, new ParameterizedTypeReference<>() {
        });
	}

	public Order createOrder(NewOrderSingle order) {
		return exchange.post(ORDERS_ENDPOINT, new ParameterizedTypeReference<>() {
        }, order);
	}

	public void cancelOrder(String orderId) {
		String deleteEndpoint = ORDERS_ENDPOINT + "/" + orderId;
        exchange.delete(deleteEndpoint, new ParameterizedTypeReference<String>() {
        });
    }

	public List<Order> getOpenOrders() {
		return exchange.getAsList(ORDERS_ENDPOINT, new ParameterizedTypeReference<>() {
        });
	}

	public List<Order> cancelAllOpenOrders() {
		return Arrays.asList(exchange.delete(ORDERS_ENDPOINT, new ParameterizedTypeReference<Order[]>() {
		}));
	}

	public List<Fill> getFillsByProductId(String product_id, int resultLimit) {
		return exchange.getAsList(FILLS_ENDPOINT + "?product_id=" + product_id + "&limit=" + resultLimit, new ParameterizedTypeReference<>() {
        });
	}

	public List<Fill> getFillByOrderId(String order_id, int resultLimit) {
		return exchange.getAsList(FILLS_ENDPOINT + "?order_id=" + order_id + "&limit=" + resultLimit, new ParameterizedTypeReference<>() {
        });
	}
}


