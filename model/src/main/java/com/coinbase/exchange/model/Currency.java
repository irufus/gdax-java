package com.coinbase.exchange.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Currency {
    private String id;
    private String name;
    private BigDecimal min_size;
    private String status;
    private String status_message;
    private BigDecimal max_precision;
    private String[] convertible_to;
    private String funding_account_id;
//TODO    private Object details;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMin_size() {
        return min_size;
    }

    public void setMin_size(BigDecimal min_size) {
        this.min_size = min_size;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_message() {
        return status_message;
    }

    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }

    public BigDecimal getMax_precision() {
        return max_precision;
    }

    public void setMax_precision(BigDecimal max_precision) {
        this.max_precision = max_precision;
    }

    public String[] getConvertible_to() {
        return convertible_to;
    }

    public void setConvertible_to(String[] convertible_to) {
        this.convertible_to = convertible_to;
    }

    public String getFunding_account_id() {
        return funding_account_id;
    }

    public void setFunding_account_id(String funding_account_id) {
        this.funding_account_id = funding_account_id;
    }
}
