package com.coinbase.exchange.api.accounts;

import com.coinbase.exchange.api.exchange.CoinbaseExchange;
import com.coinbase.exchange.model.Hold;
import lombok.Data;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

@Data
public class AccountService {

	public static final String ACCOUNTS_ENDPOINT = "/accounts";
	private final CoinbaseExchange exchange;

	public List<Account> getAccounts() {
		return exchange.getAsList(ACCOUNTS_ENDPOINT, new ParameterizedTypeReference<>() {
        });
	}

	public Account getAccount(String id) {
		return exchange.get(ACCOUNTS_ENDPOINT + "/" + id, new ParameterizedTypeReference<>() {
        });
	}

	public List<AccountHistory> getAccountHistory(String accountId) {
		String accountHistoryEndpoint = ACCOUNTS_ENDPOINT + "/" + accountId + "/ledger";
		return exchange.getAsList(accountHistoryEndpoint, new ParameterizedTypeReference<>() {
        });
	}

	public List<AccountHistory> getPagedAccountHistory(String accountId,
	                                                   String beforeOrAfter,
	                                                   Integer pageNumber,
	                                                   Integer limit) {

		String accountHistoryEndpoint = ACCOUNTS_ENDPOINT + "/" + accountId + "/ledger";
		return exchange.pagedGetAsList(accountHistoryEndpoint,
                new ParameterizedTypeReference<>() {
                },
				beforeOrAfter,
				pageNumber,
				limit);
	}

	public List<Hold> getHolds(String accountId) {
		String holdsEndpoint = ACCOUNTS_ENDPOINT + "/" + accountId + "/holds";
		return exchange.getAsList(holdsEndpoint, new ParameterizedTypeReference<>() {
        });
	}

	public List<Hold> getPagedHolds(String accountId,
	                                String beforeOrAfter,
	                                Integer pageNumber,
	                                Integer limit) {
		String holdsEndpoint = ACCOUNTS_ENDPOINT + "/" + accountId + "/holds";
		return exchange.pagedGetAsList(holdsEndpoint,
                new ParameterizedTypeReference<>() {
                },
				beforeOrAfter,
				pageNumber,
				limit);
	}

}
