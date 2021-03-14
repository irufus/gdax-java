package com.coinbase.exchange.api.transfers;

import com.coinbase.exchange.api.exchange.CoinbaseExchange;
import com.coinbase.exchange.api.exchange.CoinbaseExchangeException;
import org.springframework.core.ParameterizedTypeReference;

import java.math.BigDecimal;

/**
 * This class is best used in conjunction with the coinbase library
 * to get the coinbase account Id's. see: https://github.com/coinbase/coinbase-java
 *
 * Created by robevansuk on 15/02/2017.
 */
public class TransferService {

    static final String TRANSFER_ENDPOINT = "/transfers";

    final CoinbaseExchange coinbaseExchange;

    public TransferService(final CoinbaseExchange coinbaseExchange) {
        this.coinbaseExchange = coinbaseExchange;
    }

    /**
     * TODO untested due to lack of a coinbaseaccountID to test with.
     * @param type
     * @param amount
     * @param coinbaseAccountId
     * @return
     */
    public String transfer(String type, BigDecimal amount, String coinbaseAccountId) throws CoinbaseExchangeException {
        return coinbaseExchange.post(TRANSFER_ENDPOINT,
                new ParameterizedTypeReference<String>(){},
                new Transfer(type, amount, coinbaseAccountId));
    }

}
