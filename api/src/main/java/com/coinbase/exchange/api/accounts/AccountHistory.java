package com.coinbase.exchange.api.accounts;

import com.coinbase.exchange.model.Detail;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by irufus on 2/18/15.
 */
public class AccountHistory {
    private BigInteger id;
    private String created_at;
    private BigDecimal amount;
    private BigDecimal balance;
    private String type;
    private Detail detail;

    public AccountHistory() {}

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }
}
