package com.coinbase.exchange.api.payments;

/**
 * Created by robevansuk on 16/02/2017.
 */
public class PaymentTypes {
    String id; // UUID
    String type; // ach_bank_account
    String name; // Bank of America - eBan... ********7134
    String currency; // USD
    Boolean primary_buy;
    Boolean primary_sell;
    Boolean allow_buy;
    Boolean allow_sell;
    Boolean allow_deposit;
    Boolean allow_withdraw;
    Limit[] limits;

    public PaymentTypes() {}

    public PaymentTypes(String id,
                        String type,
                        String name,
                        String currency,
                        Boolean primary_buy,
                        Boolean primary_sell,
                        Boolean allow_buy,
                        Boolean allow_sell,
                        Boolean allow_deposit,
                        Boolean allow_withdraw,
                        Limit[] limits) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.currency = currency;
        this.primary_buy = primary_buy;
        this.primary_sell = primary_sell;
        this.allow_buy = allow_buy;
        this.allow_sell = allow_sell;
        this.allow_deposit = allow_deposit;
        this.allow_withdraw = allow_withdraw;
        this.limits = limits;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Boolean getPrimary_buy() {
        return primary_buy;
    }

    public void setPrimary_buy(Boolean primary_buy) {
        this.primary_buy = primary_buy;
    }

    public Boolean getPrimary_sell() {
        return primary_sell;
    }

    public void setPrimary_sell(Boolean primary_sell) {
        this.primary_sell = primary_sell;
    }

    public Boolean getAllow_buy() {
        return allow_buy;
    }

    public void setAllow_buy(Boolean allow_buy) {
        this.allow_buy = allow_buy;
    }

    public Boolean getAllow_sell() {
        return allow_sell;
    }

    public void setAllow_sell(Boolean allow_sell) {
        this.allow_sell = allow_sell;
    }

    public Boolean getAllow_deposit() {
        return allow_deposit;
    }

    public void setAllow_deposit(Boolean allow_deposit) {
        this.allow_deposit = allow_deposit;
    }

    public Boolean getAllow_withdraw() {
        return allow_withdraw;
    }

    public void setAllow_withdraw(Boolean allow_withdraw) {
        this.allow_withdraw = allow_withdraw;
    }

    public Limit[] getLimits() {
        return limits;
    }

    public void setLimits(Limit[] limits) {
        this.limits = limits;
    }
}
