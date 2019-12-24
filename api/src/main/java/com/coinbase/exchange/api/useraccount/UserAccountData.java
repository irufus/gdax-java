package com.coinbase.exchange.api.useraccount;

import java.math.BigDecimal;

/**
 * Created by robevansuk on 17/02/2017.
 */
public class UserAccountData {

    String product_id;
    BigDecimal exchange_volume;
    BigDecimal volume;
    String recorded_at;

    public UserAccountData() {}

    public UserAccountData(String product_id, BigDecimal exchange_volume, BigDecimal volume, String recorded_at) {
        this.product_id = product_id;
        this.exchange_volume = exchange_volume;
        this.volume = volume;
        this.recorded_at = recorded_at;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public BigDecimal getExchange_volume() {
        return exchange_volume;
    }

    public void setExchange_volume(BigDecimal exchange_volume) {
        this.exchange_volume = exchange_volume;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public String getRecorded_at() {
        return recorded_at;
    }

    public void setRecorded_at(String recorded_at) {
        this.recorded_at = recorded_at;
    }
}
