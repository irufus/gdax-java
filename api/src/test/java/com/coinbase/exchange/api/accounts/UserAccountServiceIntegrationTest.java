package com.coinbase.exchange.api.accounts;

import com.coinbase.exchange.api.BaseIntegrationTest;
import com.coinbase.exchange.api.config.IntegrationTestConfiguration;
import com.coinbase.exchange.api.exchange.CoinbaseExchangeException;
import com.coinbase.exchange.api.useraccount.UserAccountData;
import com.coinbase.exchange.api.useraccount.UserAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * See class doc for BaseIntegrationTest
 */
@ExtendWith(SpringExtension.class)
@Import({IntegrationTestConfiguration.class})
public class UserAccountServiceIntegrationTest extends BaseIntegrationTest {

    UserAccountService userAccountService;

    @BeforeEach
    void setUp() {
        this.userAccountService = new UserAccountService(exchange);
    }

    /**
     * Trailing volume could be empty so all we have to do is make sure it's not returning null
     */
    @Test
    public void getTrailingVolume() throws CoinbaseExchangeException {
        List<UserAccountData> data = userAccountService.getTrailingVolume();
        assertNotNull(data);
    }
}
