package com.coinbase.exchange.api.accounts;

import com.coinbase.exchange.api.entity.Hold;
import com.coinbase.exchange.api.exchange.GdaxExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

/**
 * Created by robevansuk on 25/01/2017.
 */
@Component
public class AccountService {

    @Autowired
    GdaxExchange exchange;

    public static final String ACCOUNTS_ENDPOINT = "/accounts";

    public Account[] getAccounts(){
        return exchange.get(ACCOUNTS_ENDPOINT, new ParameterizedTypeReference<Account[]>(){});
    }

    public Account getAccount(String id) {
        return exchange.get(ACCOUNTS_ENDPOINT + "/" + id, new ParameterizedTypeReference<Account>() {});
    }

    public AccountHistory[] getAccountHistory(String accountId) {
        String accountHistoryEndpoint = ACCOUNTS_ENDPOINT + "/" + accountId + "/ledger";
        return exchange.get(accountHistoryEndpoint, new ParameterizedTypeReference<AccountHistory[]>(){});
    }

    public AccountHistory[] getPagedAccountHistory(String accountId,
                                                       String beforeOrAfter,
                                                       Integer pageNumber,
                                                       Integer limit) {

        String accountHistoryEndpoint = ACCOUNTS_ENDPOINT + "/" + accountId + "/ledger";
        return exchange.pagedGet(accountHistoryEndpoint,
                new ParameterizedTypeReference<AccountHistory[]>(){},
                beforeOrAfter,
                pageNumber,
                limit);
    }

    public Hold[] getHolds(String accountId) {
        String holdsEndpoint = ACCOUNTS_ENDPOINT + "/" + accountId + "/holds";
        return exchange.get(holdsEndpoint, new ParameterizedTypeReference<Hold[]>(){});
    }

    public Hold[] getPagedHolds(String accountId,
                                    String beforeOrAfter,
                                    Integer pageNumber,
                                    Integer limit) {
        String holdsEndpoint = ACCOUNTS_ENDPOINT + "/" + accountId + "/holds";
        return exchange.pagedGet(holdsEndpoint,
                new ParameterizedTypeReference<Hold[]>(){},
                beforeOrAfter,
                pageNumber,
                limit);
    }

}
