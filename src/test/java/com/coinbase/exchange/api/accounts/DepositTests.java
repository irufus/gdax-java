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

import static org.junit.Assert.assertTrue;

public class DepositTests extends BaseTest {
    private final static Logger log = LoggerFactory.getLogger(DepositTests.class);

    @Autowired
    PaymentService paymentService;

    @Autowired
    AccountService accountService;

    @Autowired
    DepositService depositService;

    @Ignore
    public void depositToGDAXExchangeFromCoinbase(){
        List<CoinbaseAccount> coinbaseAccountList = paymentService.getCoinbaseAccounts();
        List<Account> gdaxAccountList = accountService.getAccounts();

        assertTrue(coinbaseAccountList.size() > 0);
        CoinbaseAccount coinbaseAccount = null;
        for(CoinbaseAccount account : coinbaseAccountList){
            if(account.getCurrency().equalsIgnoreCase("USD")){
                coinbaseAccount = account;
                break;
            }
        }

        assertTrue(gdaxAccountList.size() > 0);
        Account gdaxAccount = null;
        for(Account account: gdaxAccountList){
            if(account.getCurrency().equalsIgnoreCase("USD")){
                gdaxAccount = account;
                break;
            }
        }

        assertTrue(coinbaseAccount != null);
        assertTrue(gdaxAccount != null);
        log.info("Testing depositToGDAXExchangeFromCoinbase with " + coinbaseAccount.getId());

        BigDecimal initGDAXValue = gdaxAccount.getBalance();
        BigDecimal depositAmount = new BigDecimal(100);

        PaymentResponse response = depositService.depositViaCoinbase(depositAmount, coinbaseAccount.getCurrency(),
                coinbaseAccount.getId());
        log.info("Returned: " + response.getId());

        gdaxAccount = accountService.getAccount(gdaxAccount.getId());
        assertTrue(initGDAXValue.add(depositAmount).compareTo(gdaxAccount.getBalance()) == 0);
    }
}
