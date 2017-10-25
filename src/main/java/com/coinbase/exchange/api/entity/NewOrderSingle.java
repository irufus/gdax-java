package com.coinbase.exchange.api.entity;

import java.math.BigDecimal;

/**
 * Created by irufus on 2/25/15.
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

    public void setFunds(BigDecimal funds) {
        this.funds = funds == null ? null : funds.setScale(8, BigDecimal.ROUND_HALF_UP).toString();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "NewOrderSingle{" +
               "client_oid='" +
               client_oid +
               '\'' +
               ", type='" +
               type +
               '\'' +
               ", side='" +
               side +
               '\'' +
               ", product_id='" +
               product_id +
               '\'' +
               ", stp='" +
               stp +
               '\'' +
               ", funds='" +
               funds +
               '\'' +
               '}';
    }
}
