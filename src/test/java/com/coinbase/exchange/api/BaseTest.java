package com.coinbase.exchange.api;

import com.coinbase.exchange.api.exchange.CoinbaseExchange;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by robevansuk on 20/01/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {
                 GdaxApiApplication.class
                },
                properties = {
                    "spring.profiles.active=test"
                }
)
public class BaseTest {

    @Autowired
    public CoinbaseExchange exchange;
}
