package com.coinbase.exchange.api.accounts;

import com.coinbase.exchange.api.exchange.CoinbaseExchangeException;
import com.coinbase.exchange.model.Hold;
import com.coinbase.exchange.api.exchange.CoinbaseExchange;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

/**
 * Created by robevansuk on 25/01/2017.
 */
public class AccountService {

    final CoinbaseExchange exchange;

    public AccountService(final CoinbaseExchange exchange) {
        this.exchange = exchange;
    }

    public static final String ACCOUNTS_ENDPOINT = "/accounts";

    public List<Account> getAccounts() throws CoinbaseExchangeException {
        return exchange.getAsList(ACCOUNTS_ENDPOINT, new ParameterizedTypeReference<Account[]>(){});
    }

    public Account getAccount(String id) throws CoinbaseExchangeException {
        return exchange.get(ACCOUNTS_ENDPOINT + "/" + id, new ParameterizedTypeReference<Account>() {});
    }

    public List<AccountHistory> getAccountHistory(String accountId) throws CoinbaseExchangeException {
        String accountHistoryEndpoint = ACCOUNTS_ENDPOINT + "/" + accountId + "/ledger";
        return exchange.getAsList(accountHistoryEndpoint, new ParameterizedTypeReference<AccountHistory[]>(){});
    }

    public List<AccountHistory> getPagedAccountHistory(String accountId,
                                                       String beforeOrAfter,
                                                       Integer pageNumber,
                                                       Integer limit) throws CoinbaseExchangeException {

        String accountHistoryEndpoint = ACCOUNTS_ENDPOINT + "/" + accountId + "/ledger";
        return exchange.pagedGetAsList(accountHistoryEndpoint,
                new ParameterizedTypeReference<AccountHistory[]>(){},
                beforeOrAfter,
                pageNumber,
                limit);
    }

    public List<Hold> getHolds(String accountId) throws CoinbaseExchangeException {
        String holdsEndpoint = ACCOUNTS_ENDPOINT + "/" + accountId + "/holds";
        return exchange.getAsList(holdsEndpoint, new ParameterizedTypeReference<Hold[]>(){});
    }

    public List<Hold> getPagedHolds(String accountId,
                                    String beforeOrAfter,
                                    Integer pageNumber,
                                    Integer limit) throws CoinbaseExchangeException {
        String holdsEndpoint = ACCOUNTS_ENDPOINT + "/" + accountId + "/holds";
        return exchange.pagedGetAsList(holdsEndpoint,
                new ParameterizedTypeReference<Hold[]>(){},
                beforeOrAfter,
                pageNumber,
                limit);
    }

}
