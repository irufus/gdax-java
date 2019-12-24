package com.coinbase.exchange.api.useraccount;

import com.coinbase.exchange.api.exchange.CoinbasePro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by robevansuk on 17/02/2017.
 */
public class UserAccountService {

    private static final String USER_ACCOUNT_ENDPOINT = "/users/self/trailing-volume";

    final CoinbasePro coinbasePro;

    public UserAccountService(final CoinbasePro coinbasePro) {
        this.coinbasePro = coinbasePro;
    }

    /**
     * Returns the 30 day trailing volume information from all accounts for the key provided
     * @return UserAccountData
     */
    public List<UserAccountData> getTrailingVolume(){
        return coinbasePro.getAsList(USER_ACCOUNT_ENDPOINT, new ParameterizedTypeReference<UserAccountData[]>() {});
    }
}
