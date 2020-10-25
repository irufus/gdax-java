package com.coinbase.exchange.api.accounts;

import com.coinbase.exchange.api.BaseIntegrationTest;
import com.coinbase.exchange.api.config.IntegrationTestConfiguration;
import com.coinbase.exchange.model.Hold;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * See class doc for BaseIntegrationTest
 *
 * Created by robevansuk on 03/02/2017.
 */
@ExtendWith(SpringExtension.class)
@Import({IntegrationTestConfiguration.class})
public class AccountsIntegrationTest extends BaseIntegrationTest {

    AccountService accountService;

    @BeforeEach
    void setUp() {
        this.accountService = new AccountService(exchange);
    }

    @Test
    public void canGetAccounts() {
        List<Account> accounts  = accountService.getAccounts();
        assertNotNull(accounts);
    }

    @Test
    public void getAccount() {
        List<Account> accounts  = accountService.getAccounts();
        Account account = accountService.getAccount(accounts.get(0).getId());
        assertNotNull(account);
    }

    @Test
    public void canGetAccountHistory() {
        List<Account> accounts = accountService.getAccounts();
        List<AccountHistory> history = accountService.getAccountHistory(accounts.get(0).getId());
        assertNotNull(history); // anything but null/error.
    }

    @Test
    public void canGetAccountHolds() {
        List<Account> accounts = accountService.getAccounts();
        List<Hold> holds = accountService.getHolds(accounts.get(0).getId());
        assertNotNull(holds);
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
        assertNotNull(firstPageAccountHistory);
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

        assertNotNull(firstPageOfHolds);
        assertTrue(firstPageOfHolds.size() <= limit);
    }
}
