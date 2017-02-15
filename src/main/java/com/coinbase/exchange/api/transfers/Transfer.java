package com.coinbase.exchange.api.transfers;

import java.math.BigDecimal;

/**
 * Created by robevansuk on 15/02/2017.
 */
public class Transfer {

    String type; // deposit/withdraw
    BigDecimal amount;
    String coinbase_account_id;

    public Transfer() {}

    public Transfer(String type, BigDecimal amount, String coinbase_account_id) {
        this.type = type;
        this.amount = amount;
        this.coinbase_account_id = coinbase_account_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCoinbase_account_id() {
        return coinbase_account_id;
    }

    public void setCoinbase_account_id(String coinbase_account_id) {
        this.coinbase_account_id = coinbase_account_id;
    }
}
