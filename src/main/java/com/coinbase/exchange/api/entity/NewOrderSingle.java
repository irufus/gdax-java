package com.coinbase.exchange.api.entity;

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
public abstract class NewOrderSingle {

    private String client_oid; //optional
    private String type; //default is limit, other types are market and stop
    private String side;
    private String product_id;
    private String stp; //optional: values are dc, co , cn , cb
    private String funds;

    public String getStp() {
        return stp;
    }

    public void setStp(String stp) {
        this.stp = stp;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getClient_oid() {
        return client_oid;
    }

    public void setClient_oid(String client_oid) {
        this.client_oid = client_oid;
    }

    public String getFunds() {
        return funds;
    }

    public void setFunds(String funds) {
        this.funds = funds;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
