package com.coinbase.exchange.api;

import com.coinbase.exchange.api.exchange.CoinbasePro;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * This is an integration test. Tests extending this class should be
 * run it against the Coinbase Pro sandbox API. To do this you will need
 * to provide credentials in resources/application-test.yml
 *
 * Created by robevansuk on 20/01/2017.
 */
@SpringBootTest(properties = {
                    "spring.profiles.active=test"
                })
public abstract class BaseIntegrationTest {

    public CoinbasePro exchange;
}
