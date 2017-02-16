package com.coinbase.exchange.api.withdrawals;

import com.coinbase.exchange.api.exchange.GdaxExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by robevansuk on 16/02/2017.
 */
@Component
public class WithdrawalsService {

    @Autowired
    GdaxExchange gdaxExchange;


}
