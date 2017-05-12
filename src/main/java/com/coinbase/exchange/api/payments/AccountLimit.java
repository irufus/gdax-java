package com.coinbase.exchange.api.payments;

/**
 * Created by robevansuk on 16/02/2017.
 */
public class AccountLimit {

    Integer period_in_days;
    Amount total;
    Amount remaining;

    public AccountLimit() {}

    public AccountLimit(Integer period_in_days, Amount total, Amount remaining) {
        this.period_in_days = period_in_days;
        this.total = total;
        this.remaining = remaining;
    }

    public Integer getPeriod_in_days() {
        return period_in_days;
    }

    public void setPeriod_in_days(Integer period_in_days) {
        this.period_in_days = period_in_days;
    }

    public Amount getTotal() {
        return total;
    }

    public void setTotal(Amount total) {
        this.total = total;
    }

    public Amount getRemaining() {
        return remaining;
    }

    public void setRemaining(Amount remaining) {
        this.remaining = remaining;
    }
}
