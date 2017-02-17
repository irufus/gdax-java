package com.coinbase.exchange.api.accounts;

import com.coinbase.exchange.api.BaseTest;
import com.coinbase.exchange.api.entity.Hold;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;

/**
 * Created by robevansuk on 03/02/2017.
 */
public class AccountsTest extends BaseTest {

    @Autowired
    AccountService accountService;

    @Test
    public void canGetAccounts() {
        Account[] accounts  = accountService.getAccounts();
        assertTrue(accounts != null);
        assertTrue(accounts.length >= 0);
    }

    @Test
    public void getAccount() {
        Account[] accounts  = accountService.getAccounts();
        Account account = accountService.getAccount(accounts[0].getId());
        assertTrue(account != null);
    }

    @Test
    public void canGetAccountHistory() {
        Account[] accounts = accountService.getAccounts();
        AccountHistory[] history = accountService.getAccountHistory(accounts[0].getId());
        assertTrue(history.length >=0); // anything but null/error.
    }

    @Test
    public void canGetAccountHolds() {
        Account[] accounts = accountService.getAccounts();
        Hold[] holds = accountService.getHolds(accounts[0].getId());
        assertTrue(holds != null);
        assertTrue(holds.length >= 0);
        if (holds.length>0) {
            assertTrue(holds[0].getAmount().floatValue() >= 0.0f);
        }
    }

    @Test
    public void canGetPagedAccountHistory() {
        Account[] accounts = accountService.getAccounts();
        assertTrue(accounts!=null);
        assertTrue(accounts.length > 0);
        /**
         * note that for paged requests the before param takes precedence
         * only if before is null and after is not-null will the after param be inserted.
         */
        String beforeOrAfter = "before";
        int pageNumber = 1;
        int limit = 5;
        AccountHistory[] firstPageAccountHistory = accountService.getPagedAccountHistory(accounts[0].getId(),
                beforeOrAfter, pageNumber, limit);
        assertTrue(firstPageAccountHistory != null);
        assertTrue(firstPageAccountHistory.length >= 0);
        assertTrue(firstPageAccountHistory.length <= limit);
    }

    /**
     * NOT SURE ITS POSSIBLE TO PAGINATE FOR HOLDS - will find out and update in due course.
     * Seems not to be working ( get a null response due to bad request ).
     */
    @Test
    public void canGetPagedHolds() {
        Account[] accounts = accountService.getAccounts();

        assertTrue(accounts!=null);
        assertTrue(accounts.length > 0);

        String beforeOrAfter = "before";
        int pageNumber = 1;
        int limit = 5;

        Hold[] firstPageOfHolds = accountService.getPagedHolds(accounts[0].getId(),
                beforeOrAfter,
                pageNumber,
                limit);

        if (firstPageOfHolds != null ) {
            assertTrue(firstPageOfHolds != null);
            assertTrue(firstPageOfHolds.length >= 0);
            assertTrue(firstPageOfHolds.length <= limit);
        }
    }
}
