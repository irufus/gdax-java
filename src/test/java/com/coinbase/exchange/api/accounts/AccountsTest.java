package com.coinbase.exchange.api.accounts;

import com.coinbase.exchange.api.BaseTest;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

/**
 * Created by ren7881 on 03/02/2017.
 */
public class AccountsTest extends BaseTest {

    @Test
    public void canGetAccounts() throws CloneNotSupportedException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        Account[] accounts  = exchange.getAccounts();
        assertTrue(accounts.length >= 1);
    }


}
