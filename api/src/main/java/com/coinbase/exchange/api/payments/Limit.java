package com.coinbase.exchange.api.payments;

import java.util.Arrays;

/**
 * Created by robevansuk on 16/02/2017.
 */
public class Limit {

    AccountLimit[] buy;
    AccountLimit[] instant_buy;
    AccountLimit[] sell;
    AccountLimit[] deposit;

    public Limit() {}

    public Limit(AccountLimit[] buy, AccountLimit[] instant_buy, AccountLimit[] sell, AccountLimit[] deposit) {
        this.buy = buy;
        this.instant_buy = instant_buy;
        this.sell = sell;
        this.deposit = deposit;
    }

    public AccountLimit[] getBuy() {
        return buy;
    }

    public void setBuy(AccountLimit[] buy) {
        this.buy = buy;
    }

    public AccountLimit[] getInstant_buy() {
        return instant_buy;
    }

    public void setInstant_buy(AccountLimit[] instant_buy) {
        this.instant_buy = instant_buy;
    }

    public AccountLimit[] getSell() {
        return sell;
    }

    public void setSell(AccountLimit[] sell) {
        this.sell = sell;
    }

    public AccountLimit[] getDeposit() {
        return deposit;
    }

    public void setDeposit(AccountLimit[] deposit) {
        this.deposit = deposit;
    }

    @Override
    public String toString() {
        return "Limit{"
                + "buy=" + Arrays.toString(buy)
                + ", instant_buy=" + Arrays.toString(instant_buy)
                + ", sell=" + Arrays.toString(sell)
                + ", deposit=" + Arrays.toString(deposit)
                + '}';
    }
}
