package com.coinbase.exchange.api.payments;

import com.coinbase.exchange.api.BaseIntegrationTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class PaymentIntegrationTest extends BaseIntegrationTest {

    PaymentService testee;

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
