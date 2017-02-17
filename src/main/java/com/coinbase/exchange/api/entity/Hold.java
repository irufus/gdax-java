package com.coinbase.exchange.api.entity;

import java.math.BigDecimal;

/**
 * Created by irufus on 2/18/15.
 * Updated by robevansuk on 17/2/17
 */
public class Hold {
    String id;
    String account_id;
    String created_at;
    String update_at;
    BigDecimal amount;
    String type;
    String ref;

    public Hold () {}

    public Hold(String id, String account_id, String created_at, String update_at, BigDecimal amount, String type, String ref) {
        this.id = id;
        this.account_id = account_id;
        this.created_at = created_at;
        this.update_at = update_at;
        this.amount = amount;
        this.type = type;
        this.ref = ref;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
}
