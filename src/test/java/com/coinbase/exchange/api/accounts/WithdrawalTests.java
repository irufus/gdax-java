package com.coinbase.exchange.api.accounts;

import com.coinbase.exchange.api.BaseTest;
import com.coinbase.exchange.api.entity.PaymentResponse;
import com.coinbase.exchange.api.payments.CoinbaseAccount;
import com.coinbase.exchange.api.payments.PaymentService;
import com.coinbase.exchange.api.payments.PaymentType;
import com.coinbase.exchange.api.withdrawals.WithdrawalsService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class WithdrawalTests extends BaseTest {
    private final static Logger log  = Logger.getLogger(WithdrawalTests.class);
    @Autowired
    PaymentService paymentService;

    @Autowired
    WithdrawalsService withdrawalsService;

    @Autowired
    AccountService accountService;

    @Test
    public void withdrawToCoinbaseAccount(){
        List<Account> gdaxAccounts = accountService.getAccounts();
        List<PaymentType> paymentTypes = paymentService.getPaymentTypes();
        List<CoinbaseAccount> coinbaseAccounts = paymentService.getCoinbaseAccounts();
        assertTrue(paymentTypes.size() > 0);
        PaymentType mainType = null;
        for(PaymentType paymentType : paymentTypes){
           if(paymentType.getCurrency().equalsIgnoreCase("USD")){
               mainType = paymentType;
               break;
           }
        }
        Account gdaxAccount = null;
        for(Account account : gdaxAccounts){
            if(account.getCurrency().equalsIgnoreCase("USD")){
                gdaxAccount = account;
                break;
            }
        }
        CoinbaseAccount account = null;
        for(CoinbaseAccount coinbaseAccount : coinbaseAccounts){
            if(coinbaseAccount.getCurrency().equalsIgnoreCase("USD")){
                account = coinbaseAccount;
            }
        }
        assertTrue(gdaxAccount != null);
        assertTrue(mainType != null);
        assertTrue(account != null);
        log.info("Testing withdrawToPayment with " + mainType.getId());

        BigDecimal gdaxUSDValue = gdaxAccount.getBalance();
        BigDecimal withdrawAmount = new BigDecimal(100);
        PaymentResponse response = withdrawalsService.makeWithdrawalToCoinbase(withdrawAmount, mainType.getCurrency(), account.getId());
        assertTrue(response.getId().length() > 0 && response.getAmount().compareTo(withdrawAmount) == 0);
        log.info("Returned: " + response.getId());
        gdaxAccount = accountService.getAccount(gdaxAccount.getId());
        assertTrue(gdaxUSDValue.subtract(withdrawAmount).compareTo(gdaxAccount.getBalance()) == 0);
    }

}
