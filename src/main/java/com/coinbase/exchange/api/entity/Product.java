package com.coinbase.exchange.api.entity;

import java.math.BigDecimal;

/**
 * Created by irufus on 2/25/15.
 */
public class Product {
    private String id;
    private String base_currency;
    private String quote_currency;
    private Double base_min_size;
    private Double base_max_size;
    private Double quote_increment;

    public Double getQuote_increment() {
        return quote_increment;
    }

    public void setQuote_increment(Double quote_increment) {
        this.quote_increment = quote_increment;
    }

    public Double getBase_max_size() {
        return base_max_size;
    }

    public void setBase_max_size(Double base_max_size) {
        this.base_max_size = base_max_size;
    }

    public Double getBase_min_size() {
        return base_min_size;
    }

    public void setBase_min_size(Double base_min_size) {
        this.base_min_size = base_min_size;
    }

    public String getQuote_currency() {
        return quote_currency;
    }

    public void setQuote_currency(String quote_currency) {
        this.quote_currency = quote_currency;
    }

    public String getBase_currency() {
        return base_currency;
    }

    public void setBase_currency(String base_currency) {
        this.base_currency = base_currency;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
