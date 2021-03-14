package com.coinbase.exchange.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

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
    private double base_increment;
    private String display_name;
    private String status;
    private Boolean margin_enabled;
    private String status_message;
    private BigDecimal min_market_funds;
    private Integer max_market_funds;
    private Boolean post_only;
    private Boolean limit_only;
    private Boolean cancel_only;
    private String type;

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

    public void setBase_increment(double base_increment) {
        this.base_increment = base_increment;
    }

    public double getBase_increment() {
        return base_increment;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getMargin_enabled() {
        return margin_enabled;
    }

    public void setMargin_enabled(Boolean margin_enabled) {
        this.margin_enabled = margin_enabled;
    }

    public String getStatus_message() {
        return status_message;
    }

    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }

    public BigDecimal getMin_market_funds() {
        return min_market_funds;
    }

    public void setMin_market_funds(BigDecimal min_market_funds) {
        this.min_market_funds = min_market_funds;
    }

    public Integer getMax_market_funds() {
        return max_market_funds;
    }

    public void setMax_market_funds(Integer max_market_funds) {
        this.max_market_funds = max_market_funds;
    }

    public Boolean getPost_only() {
        return post_only;
    }

    public void setPost_only(Boolean post_only) {
        this.post_only = post_only;
    }

    public Boolean getLimit_only() {
        return limit_only;
    }

    public void setLimit_only(Boolean limit_only) {
        this.limit_only = limit_only;
    }

    public Boolean getCancel_only() {
        return cancel_only;
    }

    public void setCancel_only(Boolean cancel_only) {
        this.cancel_only = cancel_only;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Product{"
                + "id='" + id + '\''
                + ", base_currency='" + base_currency + '\''
                + ", quote_currency='" + quote_currency + '\''
                + ", base_min_size=" + base_min_size
                + ", base_max_size=" + base_max_size
                + ", quote_increment=" + quote_increment
                + ", base_increment=" + base_increment
                + ", display_name='" + display_name + '\''
                + ", status='" + status + '\''
                + ", margin_enabled=" + margin_enabled
                + ", status_message='" + status_message + '\''
                + ", min_market_funds=" + min_market_funds
                + ", max_market_funds=" + max_market_funds
                + ", post_only=" + post_only
                + ", limit_only=" + limit_only
                + ", cancel_only=" + cancel_only
                + ", type='" + type + '\''
                + '}';
    }
}
