package com.coinbase.exchange.api.payments;

import com.coinbase.exchange.api.BaseIntegrationTest;
import com.coinbase.exchange.api.config.IntegrationTestConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@Import({IntegrationTestConfiguration.class})
public class PaymentIntegrationTest extends BaseIntegrationTest {

    PaymentService testee;

    @BeforeEach
    void setUp() {
        testee = new PaymentService(exchange);
    }

    @Test
    public void hasAvailablePayments(){
        List<PaymentType> types = testee.getPaymentTypes();
        assertTrue(types.size() > 0);
    }
    @Test
    public void hasCoinbaseAccounts(){
        List<CoinbaseAccount> accounts = testee.getCoinbaseAccounts();
        assertTrue(accounts.size() > 0);
    }
}
