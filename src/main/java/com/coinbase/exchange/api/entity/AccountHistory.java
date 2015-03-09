package com.coinbase.exchange.api.entity;

import java.math.BigDecimal;

/**
 * Created by irufus on 2/18/15.
 */
public class AccountHistory {
    private Integer id;
    private String created_at;
    private BigDecimal amount;
    private BigDecimal balance;
    private String type;
    private Detail detail;
}
