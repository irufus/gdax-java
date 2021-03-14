package com.coinbase.exchange.api.authentication;

import com.coinbase.exchange.api.BaseIntegrationTest;
import com.coinbase.exchange.api.accounts.Account;
import com.coinbase.exchange.api.accounts.AccountService;
import com.coinbase.exchange.api.config.IntegrationTestConfiguration;
import com.coinbase.exchange.api.exchange.CoinbaseExchangeException;
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
 * Created by irufus (sakamura@gmail.com)
 * @Description The primary function of this class is to run through basic tests for the Authentication and GdaxExchange classes
 */
@ExtendWith(SpringExtension.class)
@Import({IntegrationTestConfiguration.class})
public class AuthenticationIntegrationIntegrationTest extends BaseIntegrationTest {

    AccountService accountService;

    @BeforeEach
    void setUp() {
        accountService = new AccountService(exchange);
    }

    // ensure a basic request can be made. Not a great test. Improve.
    @Test
    public void simpleAuthenticationTest() throws CoinbaseExchangeException {
        List<Account> accounts = accountService.getAccounts();
        assertNotNull(accounts);
        assertTrue(accounts.size() > 0);
    }

}
