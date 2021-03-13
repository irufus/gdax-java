package com.coinbase.exchange.api.accounts;

import com.coinbase.exchange.api.BaseIntegrationTest;
import com.coinbase.exchange.api.config.IntegrationTestConfiguration;
import com.coinbase.exchange.api.deposits.DepositService;
import com.coinbase.exchange.api.exchange.CoinbaseExchangeException;
import com.coinbase.exchange.api.payments.CoinbaseAccount;
import com.coinbase.exchange.api.payments.PaymentService;
import com.coinbase.exchange.model.PaymentResponse;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * See class doc for BaseIntegrationTest
 */
@ExtendWith(SpringExtension.class)
@Import({IntegrationTestConfiguration.class})
@Ignore
public class DepositIntegrationTest extends BaseIntegrationTest {
    private final static Logger log = LoggerFactory.getLogger(DepositIntegrationTest.class);

    PaymentService paymentService;
    AccountService accountService;
    DepositService testee;

    @BeforeEach
    void setUp() {
        this.paymentService = new PaymentService(exchange);
        this.accountService = new AccountService(exchange);
        this.testee = new DepositService(exchange);
    }

    @Test
    public void depositToGDAXExchangeFromCoinbase() throws CoinbaseExchangeException {
        // given
        List<CoinbaseAccount> coinbaseAccountList = paymentService.getCoinbaseAccounts();
        assertTrue(coinbaseAccountList.size() > 0);
        List<Account> gdaxAccountList = accountService.getAccounts();
        assertTrue(gdaxAccountList.size() > 0);
        CoinbaseAccount coinbaseAccount = getCoinbaseAccount(coinbaseAccountList);
        Account gdaxAccount = getAccount(gdaxAccountList);
        log.info("Testing depositToGDAXExchangeFromCoinbase with " + coinbaseAccount.getId());

        BigDecimal initGDAXValue = gdaxAccount.getBalance();
        BigDecimal depositAmount = new BigDecimal(100);

        PaymentResponse response = testee.depositViaCoinbase(depositAmount, coinbaseAccount.getCurrency(), coinbaseAccount.getId());
        log.info("Returned: " + response.getId());

        // when
        gdaxAccount = accountService.getAccount(gdaxAccount.getId());

        // then
        assertEquals(0, initGDAXValue.add(depositAmount).compareTo(gdaxAccount.getBalance()));
    }

    private Account getAccount(List<Account> gdaxAccountList) {
        Account gdaxAccount = null;
        for(Account account: gdaxAccountList){
            if(account.getCurrency().equalsIgnoreCase("USD")){
                gdaxAccount = account;
                break;
            }
        }
        assertNotNull(gdaxAccount);
        return gdaxAccount;
    }

    private CoinbaseAccount getCoinbaseAccount(List<CoinbaseAccount> coinbaseAccountList) {
        CoinbaseAccount coinbaseAccount = null;
        for(CoinbaseAccount account : coinbaseAccountList){
            if(account.getCurrency().equalsIgnoreCase("USD")){
                coinbaseAccount = account;
                break;
            }
        }
        assertNotNull(coinbaseAccount);
        return coinbaseAccount;
    }
}
