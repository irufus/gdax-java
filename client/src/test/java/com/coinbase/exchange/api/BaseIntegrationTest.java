package com.coinbase.exchange.api;

import com.coinbase.exchange.api.exchange.GdaxExchange;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * This is an integration test. Tests extending this class should be
 * run it against the Coinbase Pro sandbox API. To do this you will need
 * to provide credentials in resources/application-test.yml
 *
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
public abstract class BaseIntegrationTest {

    @Autowired
    public GdaxExchange exchange;
}
