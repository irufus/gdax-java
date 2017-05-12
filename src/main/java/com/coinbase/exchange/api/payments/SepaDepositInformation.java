package com.coinbase.exchange.api.payments;

/**
 * Created by robevansuk on 16/02/2017.
 */
public class SepaDepositInformation {

    String iban;
    String swift;
    String bank_name;
    String bank_address;
    String bank_country_name;
    String account_name;
    String account_address;
    String reference;

    public SepaDepositInformation() {}

    public SepaDepositInformation(String iban,
                                  String swift,
                                  String bank_name,
                                  String bank_address,
                                  String bank_country_name,
                                  String account_name,
                                  String account_address,
                                  String reference) {
        this.iban = iban;
        this.swift = swift;
        this.bank_name = bank_name;
        this.bank_address = bank_address;
        this.bank_country_name = bank_country_name;
        this.account_name = account_name;
        this.account_address = account_address;
        this.reference = reference;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getSwift() {
        return swift;
    }

    public void setSwift(String swift) {
        this.swift = swift;
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

    public String getBank_country_name() {
        return bank_country_name;
    }

    public void setBank_country_name(String bank_country_name) {
        this.bank_country_name = bank_country_name;
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
