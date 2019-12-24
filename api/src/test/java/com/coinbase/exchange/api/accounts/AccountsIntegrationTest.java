package com.coinbase.exchange.api.accounts;

import com.coinbase.exchange.api.BaseIntegrationTest;
import com.coinbase.exchange.api.entity.Hold;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * See class doc for BaseIntegrationTest
 *
 * Created by robevansuk on 03/02/2017.
 */
public class AccountsIntegrationTest extends BaseIntegrationTest {

    AccountService accountService;

    @Test
    public void canGetAccounts() {
        List<Account> accounts  = accountService.getAccounts();
        assertTrue(accounts != null);
    }

    @Test
    public void getAccount() {
        List<Account> accounts  = accountService.getAccounts();
        Account account = accountService.getAccount(accounts.get(0).getId());
        assertTrue(account != null);
    }

    @Test
    public void canGetAccountHistory() {
        List<Account> accounts = accountService.getAccounts();
        List<AccountHistory> history = accountService.getAccountHistory(accounts.get(0).getId());
        assertTrue(history != null); // anything but null/error.
    }

    @Test
    public void canGetAccountHolds() {
        List<Account> accounts = accountService.getAccounts();
        List<Hold> holds = accountService.getHolds(accounts.get(0).getId());
        assertTrue(holds != null);
        // only check the holds if they exist
        if (holds.size()>0) {
            assertTrue(holds.get(0).getAmount().floatValue() >= 0.0f);
        }
    }

    /**
     * note that for paged requests the before param takes precedence
     * only if before is null and after is not-null will the after param be inserted.
     */
    @Test
    public void canGetPagedAccountHistory() {
        List<Account> accounts = accountService.getAccounts();
        assertTrue(accounts.size() > 0);
        String beforeOrAfter = "before";
        int pageNumber = 1;
        int limit = 5;
        List<AccountHistory> firstPageAccountHistory = accountService.getPagedAccountHistory(accounts.get(0).getId(),
                beforeOrAfter, pageNumber, limit);
        assertTrue(firstPageAccountHistory != null);
        assertTrue(firstPageAccountHistory.size() <= limit);
    }

    @Test
    public void canGetPagedHolds() {
        List<Account> accounts = accountService.getAccounts();

        assertTrue(accounts!=null);
        assertTrue(accounts.size() > 0);

        String beforeOrAfter = "after";
        int pageNumber = 1;
        int limit = 5;

        List<Hold> firstPageOfHolds = accountService.getPagedHolds(accounts.get(0).getId(),
                beforeOrAfter,
                pageNumber,
                limit);

        assertTrue(firstPageOfHolds != null);
        assertTrue(firstPageOfHolds.size() <= limit);
    }
}
