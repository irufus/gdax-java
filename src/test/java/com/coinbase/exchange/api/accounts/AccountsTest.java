package com.coinbase.exchange.api.accounts;

import com.coinbase.exchange.api.BaseTest;
import com.coinbase.exchange.api.entity.Hold;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.InvalidKeyException;

import static org.junit.Assert.*;

/**
 * Created by robevansuk on 03/02/2017.
 */
public class AccountsTest extends BaseTest {

    @Autowired
    AccountService accountService;

    @Test
    public void canGetAccounts() {
        Account[] accounts  = accountService.getAccounts();
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
        assertTrue(holds.length >=0); // anything but null/error.
    }
}
