package com.coinbase.exchange.api.accounts;

import com.coinbase.exchange.api.BaseTest;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by irufus (sakamura@gmail.com)
 * @Description The primary function of this class is to run through basic tests for the Authentication and GdaxExchange classes
 */
public class AuthenticationTests extends BaseTest {

    AccountService accountService;

    // ensure a basic request can be made. Not a great test. Improve.
    @Ignore
    @Test
    public void simpleAuthenticationTest(){
        List<Account> accounts = accountService.getAccounts();
        assertTrue(accounts != null);
        assertTrue(accounts.size() > 0);
    }

}
