package com.coinbase.exchange.api.gui.config;

import com.coinbase.exchange.api.exchange.CoinbaseExchange;
import com.coinbase.exchange.api.exchange.CoinbaseExchangeImpl;
import com.coinbase.exchange.api.exchange.Signature;
import com.coinbase.exchange.api.websocketfeed.WebsocketFeed;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootConfiguration
public class DesktopConfiguration {

    @Bean
    public CoinbaseExchange coinbasePro(@Value("${gdax.key}") String publicKey,
                                        @Value("${gdax.passphrase}") String passphrase,
                                        @Value("${gdax.api.baseUrl}") String baseUrl,
                                        Signature signature,
                                        RestTemplate restTemplate) {
        return new CoinbaseExchangeImpl(publicKey, passphrase, baseUrl, signature, restTemplate);
    }

    @Bean
    public Signature signature(@Value("${gdax.secret}") String secret) {
        return new Signature(secret);
    }

    @Bean
    public WebsocketFeed websocketFeed(@Value("${websocket.baseUrl}") String websocketUrl,
                                       @Value("${gdax.key}") String apiKey,
                                       @Value("${gdax.passphrase}") String passphrase,
                                       @Value("${gui.enabled}") boolean guiEnabled,
                                       Signature signature) {
        return new WebsocketFeed(websocketUrl,
                apiKey,
                passphrase,
                guiEnabled,
                signature);
    }


}
