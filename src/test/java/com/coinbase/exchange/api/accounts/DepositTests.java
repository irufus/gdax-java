package com.coinbase.exchange.api.accounts;

import com.coinbase.exchange.api.BaseTest;
import com.coinbase.exchange.api.deposits.DepositService;
import com.coinbase.exchange.api.entity.PaymentResponse;
import com.coinbase.exchange.api.payments.CoinbaseAccount;
import com.coinbase.exchange.api.payments.PaymentService;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Ignore
public class DepositTests extends BaseTest {
    private final static Logger log = LoggerFactory.getLogger(DepositTests.class);

    @Autowired
    PaymentService paymentService;

    @Autowired
    AccountService accountService;

    @Autowired
    DepositService depositService;

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

        PaymentResponse response = depositService.depositViaCoinbase(depositAmount, coinbaseAccount.getCurrency(), coinbaseAccount.getId());
        log.info("Returned: " + response.getId());

        // when
        gdaxAccount = accountService.getAccount(gdaxAccount.getId());

        // then
        assertTrue(initGDAXValue.add(depositAmount).compareTo(gdaxAccount.getBalance()) == 0);
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
