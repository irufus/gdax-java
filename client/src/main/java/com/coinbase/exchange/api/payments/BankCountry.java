package com.coinbase.exchange.api.payments;

/**
 * Created by robevansuk on 16/02/2017.
 */
public class BankCountry {

    String code;
    String name;

    public BankCountry() {}

    public BankCountry(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
