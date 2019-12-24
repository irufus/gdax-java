package com.coinbase.exchange.api.authentication;

import com.coinbase.exchange.api.BaseIntegrationTest;
import com.coinbase.exchange.api.accounts.Account;
import com.coinbase.exchange.api.accounts.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * See class doc for BaseIntegrationTest
 *
 * Created by irufus (sakamura@gmail.com)
 * @Description The primary function of this class is to run through basic tests for the Authentication and GdaxExchange classes
 */
public class AuthenticationIntegrationIntegrationTest extends BaseIntegrationTest {

    AccountService accountService; // TODO Mock

    // ensure a basic request can be made. Not a great test. Improve.
    @Test
    public void simpleAuthenticationTest(){
        List<Account> accounts = accountService.getAccounts();
        assertTrue(accounts != null);
        assertTrue(accounts.size() > 0);
    }

}
