package com.coinbase.exchange.api.payments;

import java.math.BigDecimal;

/**
 * Created by robevansuk on 16/02/2017.
 */
public class CoinbaseAccounts {

    String id; //UUID
    String name;
    BigDecimal balance;
    String currency;
    String type;
    Boolean primary;
    Boolean active;
    DepositInformation wire_deposit_information;
    SepaDepositInformation sepa_deposit_information;

    public CoinbaseAccounts() {}

    public CoinbaseAccounts(String id,
                            String name,
                            BigDecimal balance,
                            String currency,
                            String type,
                            Boolean primary,
                            Boolean active,
                            DepositInformation wire_deposit_information,
                            SepaDepositInformation sepa_deposit_information) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.currency = currency;
        this.type = type;
        this.primary = primary;
        this.active = active;
        this.wire_deposit_information = wire_deposit_information;
        this.sepa_deposit_information = sepa_deposit_information;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getPrimary() {
        return primary;
    }

    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public DepositInformation getWire_deposit_information() {
        return wire_deposit_information;
    }

    public void setWire_deposit_information(DepositInformation wire_deposit_information) {
        this.wire_deposit_information = wire_deposit_information;
    }

    public SepaDepositInformation getSepa_deposit_information() {
        return sepa_deposit_information;
    }

    public void setSepa_deposit_information(SepaDepositInformation sepa_deposit_information) {
        this.sepa_deposit_information = sepa_deposit_information;
    }

    @Override
    public String toString()
    {
        return "CoinbaseAccounts{" +
               "id='" +
               id +
               '\'' +
               ", name='" +
               name +
               '\'' +
               ", balance=" +
               balance +
               ", currency='" +
               currency +
               '\'' +
               ", type='" +
               type +
               '\'' +
               ", primary=" +
               primary +
               ", active=" +
               active +
               ", wire_deposit_information=" +
               wire_deposit_information +
               ", sepa_deposit_information=" +
               sepa_deposit_information +
               '}';
    }
}
