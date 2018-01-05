package com.coinbase.exchange.api.accounts;

import com.coinbase.exchange.api.config.GdaxStaticVariables;
import com.coinbase.exchange.api.entity.Hold;
import com.coinbase.exchange.api.exchange.GdaxExchange;

import java.util.List;

/**
 * Created by robevansuk on 25/01/2017.
 */

public class AccountService {


    GdaxExchange exchange;

    public List<Account> getAccounts(){
        return exchange.getAsList(GdaxStaticVariables.ACCOUNTS_ROOT, Account[].class);
    }

    public Account getAccount(String id) {
        return exchange.get(GdaxStaticVariables.ACCOUNTS_ROOT + "/" + id, Account.class);
    }

    public List<AccountHistory> getAccountHistory(String accountId) {
        String accountHistoryEndpoint = GdaxStaticVariables.ACCOUNTS_ROOT + "/" + accountId + GdaxStaticVariables.ACCOUNTS_LEDGER;
        return exchange.getAsList(accountHistoryEndpoint, AccountHistory[].class);
    }

    public List<AccountHistory> getPagedAccountHistory(String accountId,
                                                       String beforeOrAfter,
                                                       Integer pageNumber,
                                                       Integer limit) {

        String accountHistoryEndpoint = GdaxStaticVariables.ACCOUNTS_ROOT + "/" + accountId + GdaxStaticVariables.ACCOUNTS_LEDGER;
        return exchange.pagedGetAsList(accountHistoryEndpoint,
                AccountHistory[].class,
                beforeOrAfter,
                pageNumber,
                limit);
    }

    public List<Hold> getHolds(String accountId) {
        String holdsEndpoint = GdaxStaticVariables.ACCOUNTS_ROOT + "/" + accountId + GdaxStaticVariables.ACCOUNTS_HOLDS;
        return exchange.getAsList(holdsEndpoint, Hold[].class);
    }

    public List<Hold> getPagedHolds(String accountId,
                                    String beforeOrAfter,
                                    Integer pageNumber,
                                    Integer limit) {
        String holdsEndpoint = GdaxStaticVariables.ACCOUNTS_ROOT + "/" + accountId + GdaxStaticVariables.ACCOUNTS_HOLDS;
        return exchange.pagedGetAsList(holdsEndpoint,
                Hold[].class,
                beforeOrAfter,
                pageNumber,
                limit);
    }

}
