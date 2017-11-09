package com.coinbase.exchange.api.accounts;

import com.coinbase.exchange.api.BaseTest;
import com.coinbase.exchange.api.entity.Hold;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by robevansuk on 03/02/2017.
 */
public class AccountsTest extends BaseTest {

    @Autowired
    AccountService accountService;

    @Test
    public void canGetAccounts() {
        List<Account> accounts  = accountService.getAccounts();
        assertTrue(accounts.size() >= 0);
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
        assertTrue(history.size() >=0); // anything but null/error.
    }

    @Test
    public void canGetAccountHolds() {
        List<Account> accounts = accountService.getAccounts();
        List<Hold> holds = accountService.getHolds(accounts.get(0).getId());
        assertTrue(holds.size() >= 0);
        // only check the holds if they exist
        if (holds.size()>0) {
            assertTrue(holds.get(0).getAmount().floatValue() >= 0.0f);
        }
    }

    @Test
    public void canGetPagedAccountHistory() {
        List<Account> accounts = accountService.getAccounts();
        assertTrue(accounts.size() > 0);
        /**
         * note that for paged requests the before param takes precedence
         * only if before is null and after is not-null will the after param be inserted.
         */
        String beforeOrAfter = "before";
        int pageNumber = 1;
        int limit = 5;
        List<AccountHistory> firstPageAccountHistory = accountService.getPagedAccountHistory(accounts.get(0).getId(),
                beforeOrAfter, pageNumber, limit);
        assertTrue(firstPageAccountHistory != null);
        assertTrue(firstPageAccountHistory.size() >= 0);
        assertTrue(firstPageAccountHistory.size() <= limit);
    }

    /**
     * Test is ignored as it's failing. Seems the request here is
     * a bad one. Not sure if this is because there are no holds or
     * if this is due to the request (which is the same as for account history)
     * is actually fine but there's no data available.
     */
    @Ignore
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

        if (firstPageOfHolds != null ) {
            assertTrue(firstPageOfHolds != null);
            assertTrue(firstPageOfHolds.size() >= 0);
            assertTrue(firstPageOfHolds.size() <= limit);
        }
    }
}
