package com.coinbase.exchange.api.authentication;

import com.coinbase.exchange.api.BaseTest;
import com.coinbase.exchange.api.accounts.Account;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

/**
 * Created by irufus (sakamura@gmail.com)
 * @Description The primary function of this class is to run through basic tests for the Authentication and CoinbaseExchange classes
 */
public class AuthenticationTests extends BaseTest {

    @Test
    public void simpleAuthenticationTest(){
        try {
            Account[] accounts = exchange.getAccounts();
            assertTrue(accounts.length > 0);
        }
        catch(Exception ex){
            ex.printStackTrace();
            fail();
        }
    }

}
