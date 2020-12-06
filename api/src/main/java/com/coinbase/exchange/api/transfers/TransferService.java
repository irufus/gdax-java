package com.coinbase.exchange.api.transfers;

import com.coinbase.exchange.api.exchange.CoinbaseExchange;
import lombok.Data;
import org.springframework.core.ParameterizedTypeReference;

import java.math.BigDecimal;


/**
 * This class is best used in conjunction with the coinbase library
 * to get the coinbase account Id's. see: https://github.com/coinbase/coinbase-java
 */
@Data
public class TransferService {

    static final String TRANSFER_ENDPOINT = "/transfers";

    private final CoinbaseExchange coinbaseExchange;

    /**
     * TODO untested due to lack of a coinbaseaccountID to test with.
     * @param type
     * @param amount
     * @param coinbaseAccountId
     * @return
     */
    public String transfer(String type, BigDecimal amount, String coinbaseAccountId) {
        return coinbaseExchange.post(TRANSFER_ENDPOINT,
                new ParameterizedTypeReference<>() {
                },
                new Transfer(type, amount, coinbaseAccountId));
    }

}
