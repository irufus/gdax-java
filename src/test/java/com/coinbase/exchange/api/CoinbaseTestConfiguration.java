package com.coinbase.exchange.api;
;
import com.coinbase.exchange.api.authentication.Authentication;
import com.coinbase.exchange.api.exchange.CoinbaseExchange;
import com.coinbase.exchange.api.exchange.CoinbaseExchangeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by robevansuk on 20/01/2017.
 */
@TestPropertySource("classpath:/application.yml")
@SpringBootConfiguration
public class CoinbaseTestConfiguration {

    /**
     * this will generate a coinbaseExchange object and store it as a singleton
     * for use throughout the test application
     */
    @Autowired
    @Bean
    public CoinbaseExchange coinbaseExchange(@Value("${gdax.api.url}")String url,
                                             Authentication authentication) {
        CoinbaseExchange exchange = null;

        if (url!=null && !url.equals("https://api.gdax.com/")) {
//            throw new RuntimeErrorException(new Error("Testing against production"),
//                    "Comment out this exception if you want to test against production.");
        }

        try {
            exchange = new CoinbaseExchangeBuilder()
                    .withAPIUrl(url)
                    .withAuthentication(authentication)
                    .build();
        } catch(NoSuchAlgorithmException nsaex){
            nsaex.printStackTrace();
            System.out.println("Algorithm not supported");
        } catch(MalformedURLException murlEx) {
            murlEx.printStackTrace();
            System.out.println("URL Incorrect. Check it again: \'" + url + "\'");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return exchange;
    }

    @Autowired
    @Bean
    public Authentication authentication(@Value("${gdax.key}") String publicKey,
                                         @Value("${gdax.secret}") String secretKey,
                                         @Value("${gdax.passphrase}") String passphrase) {
        return new Authentication(publicKey, secretKey, passphrase);
    }
}
