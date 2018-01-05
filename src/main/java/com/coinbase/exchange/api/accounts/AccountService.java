package com.coinbase.exchange.api.accounts;

import com.coinbase.exchange.api.config.GdaxStaticVariables;
import com.coinbase.exchange.api.entity.Hold;
import com.coinbase.exchange.api.exchange.GdaxExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by robevansuk on 25/01/2017.
 */
@Component
public class AccountService {

    @Autowired
    GdaxExchange exchange;

    public List<Account> getAccounts(){
        return exchange.getAsList(GdaxStaticVariables.ACCOUNTS_ROOT, Account[].class);
    }

    public Account getAccount(String id) {
        return exchange.get(GdaxStaticVariables.ACCOUNTS_ROOT + "/" + id, Account.class);
    }

    public List<AccountHistory> getAccountHistory(String accountId) {
        String accountHistoryEndpoint = GdaxStaticVariables.ACCOUNTS_ROOT + "/" + accountId + GdaxStaticVariables.ACCOUNTS_LEDGER;
        return exchange.getAsList(accountHistoryEndpoint, new ParameterizedTypeReference<AccountHistory[]>(){});
    }

    public List<AccountHistory> getPagedAccountHistory(String accountId,
                                                       String beforeOrAfter,
                                                       Integer pageNumber,
                                                       Integer limit) {

        String accountHistoryEndpoint = GdaxStaticVariables.ACCOUNTS_ROOT + "/" + accountId + GdaxStaticVariables.ACCOUNTS_LEDGER;
        return exchange.pagedGetAsList(accountHistoryEndpoint,
                new ParameterizedTypeReference<AccountHistory[]>(){},
                beforeOrAfter,
                pageNumber,
                limit);
    }

    public List<Hold> getHolds(String accountId) {
        String holdsEndpoint = GdaxStaticVariables.ACCOUNTS_ROOT + "/" + accountId + GdaxStaticVariables.ACCOUNTS_HOLDS;
        return exchange.getAsList(holdsEndpoint, new ParameterizedTypeReference<Hold[]>(){});
    }

    public List<Hold> getPagedHolds(String accountId,
                                    String beforeOrAfter,
                                    Integer pageNumber,
                                    Integer limit) {
        String holdsEndpoint = GdaxStaticVariables.ACCOUNTS_ROOT + "/" + accountId + GdaxStaticVariables.ACCOUNTS_HOLDS;
        return exchange.pagedGetAsList(holdsEndpoint,
                new ParameterizedTypeReference<Hold[]>(){},
                beforeOrAfter,
                pageNumber,
                limit);
    }

}
