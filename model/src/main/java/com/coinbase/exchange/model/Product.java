package com.coinbase.exchange.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * <pre>
 *     {
 *       "id": "BTC-USD",
 *       "base_currency": "BTC",
 *       "quote_currency": "USD",
 *       "base_min_size": "0.001",
 *       "base_max_size": "280",
 *       "base_increment": "0.00000001",
 *       "quote_increment": "0.01",
 *       "display_name": "BTC/USD",
 *       "status": "online",
 *       "margin_enabled": false,
 *       "status_message": "",
 *       "min_market_funds": "5",
 *       "max_market_funds": "1000000",
 *       "post_only": false,
 *       "limit_only": false,
 *       "cancel_only": false,
 *       "type": "spot"
 *     }
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    private String id;
    private String base_currency;
    private String quote_currency;
    private Double base_min_size;
    private Double base_max_size;
    private Double quote_increment;

/**
 *   TODO
 *   Object base_increment;
 *   Object display_name;
 *   Object status;
 *   Object status_message;
 *   Object min_market_funds;
 *   Object max_market_funds;
 *   Object post_only;
 *   Object limit_only;
 *   Object cancel_only;
 **/

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
