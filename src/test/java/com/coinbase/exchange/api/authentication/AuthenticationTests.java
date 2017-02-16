package com.coinbase.exchange.api.authentication;

import com.coinbase.exchange.api.BaseTest;
import com.coinbase.exchange.api.accounts.Account;
import com.coinbase.exchange.api.accounts.AccountService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

/**
 * Created by irufus (sakamura@gmail.com)
 * @Description The primary function of this class is to run through basic tests for the Authentication and GdaxExchange classes
 */
public class AuthenticationTests extends BaseTest {

    @Autowired
    AccountService accountService;

    @Test
    public void simpleAuthenticationTest(){
        Account[] accounts = accountService.getAccounts();
        assertTrue(accounts.length > 0);
    }

}
