package com.coinbase.exchange.api;

import com.coinbase.exchange.api.exchange.CoinbaseExchange;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by robevansuk on 20/01/2017.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {CoinbaseTestConfiguration.class })
@SpringBootTest(classes = {
        Application.class
})
public class BaseTest {

    @Autowired
    CoinbaseExchange exchange;
}
