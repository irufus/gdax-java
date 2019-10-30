package com.coinbase.exchange.api.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Created by robevansuk on 07/02/2017.
 */
@SpringBootConfiguration
public class GdaxConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
