package com.coinbase.exchange.api.accounts;

import com.coinbase.exchange.api.BaseTest;
import com.coinbase.exchange.api.useraccount.UserAccountData;
import com.coinbase.exchange.api.useraccount.UserAccountService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class UserAccountServiceTests extends BaseTest {

    @Autowired
    UserAccountService userAccountService;

    /**
     * Trailing volume could be empty so all we have to do is make sure it's not returning null
     */
    @Test
    public void getTrailingVolume(){
        List<UserAccountData> data = userAccountService.getTrailingVolume();
        assertTrue(data != null);
    }
}
