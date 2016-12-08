package com.coinbase.exchange.api.accounts;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by robevansuk on 20/01/2017.
 */
@RequestMapping("/accounts")
public class AccountsController {

    @Value("gdax.api.url")
    private String gdaxBaseUrl;

}
