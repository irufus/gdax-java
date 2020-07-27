package com.coinbase.exchange.api.payments;

/**
 * Created by robevansuk on 16/02/2017.
 */
public class DepositInformation {

    String account_number;
    String routing_number;
    String bank_name;
    String bank_address;
    BankCountry bank_country;
    String account_name;
    String account_address;
    String reference;

    public DepositInformation() {}

    public DepositInformation(String account_number,
                              String routing_number,
                              String bank_name,
                              String bank_address,
                              BankCountry bank_country,
                              String account_name,
                              String account_address,
                              String reference) {
        this.account_number = account_number;
        this.routing_number = routing_number;
        this.bank_name = bank_name;
        this.bank_address = bank_address;
        this.bank_country = bank_country;
        this.account_name = account_name;
        this.account_address = account_address;
        this.reference = reference;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getRouting_number() {
        return routing_number;
    }

    public void setRouting_number(String routing_number) {
        this.routing_number = routing_number;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_address() {
        return bank_address;
    }

    public void setBank_address(String bank_address) {
        this.bank_address = bank_address;
    }

    public BankCountry getBank_country() {
        return bank_country;
    }

    public void setBank_country(BankCountry bank_country) {
        this.bank_country = bank_country;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getAccount_address() {
        return account_address;
    }

    public void setAccount_address(String account_address) {
        this.account_address = account_address;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
