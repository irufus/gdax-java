package com.coinbase.exchange.api.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
	@JsonProperty("id")
	private String id;
	@JsonProperty("size")
	private String size;
	@JsonProperty("price")
	private String price;
	@JsonProperty("product_id")
	private String productId;
	@JsonProperty("side")
	private String side;
	@JsonProperty("stp")
	private String stp;
	@JsonProperty("type")
	private String type;
	@JsonProperty("time_in_force")
	private String timeInForce;
	@JsonProperty("post_only")
	private String postOnly;
	@JsonProperty("created_at")
	private String createdAt;
	@JsonProperty("fill_fees")
	private String fillFees;
	@JsonProperty("filled_size")
	private String filledSize;
	@JsonProperty("executed_value")
	private String executedValue;
	@JsonProperty("status")
	private String status;
	@JsonProperty("settled")
	private Boolean settled;

	public Order(OrderBuilder builder) {
		this.id = builder.getId();
		this.size = builder.getSize();
		this.price = builder.getPrice();
		this.productId = builder.getProductId();
		this.status = builder.getStatus();
		this.filledSize = builder.getFilledSize();
		this.fillFees = builder.getFillFees();
		this.settled = builder.getSettled();
		this.side = builder.getSide();
		this.createdAt = builder.getCreatedAt();
	}

	public String toString() {
		String orderString = getSide();
		orderString += ": " + getProductId();
		orderString += ": " + getPrice();
		orderString += ": " + getSize();
		return orderString;
	}
}
