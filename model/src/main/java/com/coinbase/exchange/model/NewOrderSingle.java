package com.coinbase.exchange.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * <pre>
 * {
 *     "id": "d0c5340b-6d6c-49d9-b567-48c4bfca13d2",
 *     "price": "0.10000000",
 *     "size": "0.01000000",
 *     "product_id": "BTC-USD",
 *     "side": "buy",
 *     "stp": "dc",
 *     "type": "limit",
 *     "time_in_force": "GTC",
 *     "post_only": false,
 *     "created_at": "2016-12-08T20:02:28.53864Z",
 *     "fill_fees": "0.0000000000000000",
 *     "filled_size": "0.00000000",
 *     "executed_value": "0.0000000000000000",
 *     "status": "pending",
 *     "settled": false
 * }
 * </pre>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class NewOrderSingle {
    @JsonProperty("client_oid")
    private String clientOid; //optional
    @JsonProperty("type")
    private String type; //default is limit, other types are market and stop
    @JsonProperty("side")
    private String side;
    @JsonProperty("product_id")
    private String productId;
    @JsonProperty("stp")
    private String stp; //optional: values are dc, co , cn , cb
    @JsonProperty("funds")
    private String funds;
}
