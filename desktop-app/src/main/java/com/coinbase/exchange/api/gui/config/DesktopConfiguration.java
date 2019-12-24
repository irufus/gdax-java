package com.coinbase.exchange.api.gui.config;

import com.coinbase.exchange.api.exchange.Signature;
import com.coinbase.exchange.api.websocketfeed.WebsocketFeed;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class DesktopConfiguration {
    @Bean
    public WebsocketFeed websocketFeed(@Value("${websocket.baseUrl}") String websocketUrl,
                                       @Value("${gdax.key}") String key,
                                       @Value("${gdax.passphrase}") String passphrase,
                                       @Value("${gui.enabled}") boolean guiEnabled,
                                       Signature signature) {
        return new WebsocketFeed(websocketUrl,
                key,
                passphrase,
                guiEnabled,
                signature);
    }
}
