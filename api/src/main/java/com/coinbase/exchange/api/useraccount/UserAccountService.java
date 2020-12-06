package com.coinbase.exchange.api.useraccount;

import com.coinbase.exchange.api.exchange.CoinbaseExchange;
import lombok.Data;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

@Data
public class UserAccountService {

    private static final String USER_ACCOUNT_ENDPOINT = "/users/self/trailing-volume";

    private final CoinbaseExchange coinbaseExchange;

    /**
     * Returns the 30 day trailing volume information from all accounts
     * @return UserAccountData
     */
    public List<UserAccountData> getTrailingVolume(){
        return coinbaseExchange.getAsList(USER_ACCOUNT_ENDPOINT, new ParameterizedTypeReference<>() {
        });
    }
}
