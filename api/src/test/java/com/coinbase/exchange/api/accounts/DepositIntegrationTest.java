package com.coinbase.exchange.api.accounts;

import com.coinbase.exchange.api.BaseIntegrationTest;
import com.coinbase.exchange.api.deposits.DepositService;
import com.coinbase.exchange.api.payments.CoinbaseAccount;
import com.coinbase.exchange.api.payments.PaymentService;
import com.coinbase.exchange.model.PaymentResponse;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * See class doc for BaseIntegrationTest
 */
@Ignore
public class DepositIntegrationTest extends BaseIntegrationTest {
    private final static Logger log = LoggerFactory.getLogger(DepositIntegrationTest.class);

    PaymentService paymentService;// TODO Mock
    AccountService accountService;// TODO Mock
    DepositService testee;

    @Test
    public void depositToGDAXExchangeFromCoinbase(){
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
