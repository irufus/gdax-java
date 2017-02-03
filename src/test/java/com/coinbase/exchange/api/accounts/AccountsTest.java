package com.coinbase.exchange.api.accounts;

import com.coinbase.exchange.api.BaseTest;
import com.coinbase.exchange.api.entity.Account;
import org.junit.Test;

/**
 * Created by ren7881 on 03/02/2017.
 */
public class AccountsTest extends BaseTest {

    @Test
    public void canGetAccounts(){
        Account[] accounts  = exchange.getAccounts();
        for ( Account acc : accounts);

    }


}
