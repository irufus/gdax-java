package com.coinbase.exchange.api.gui.config;

import com.coinbase.exchange.api.exchange.CoinbaseExchange;
import com.coinbase.exchange.api.exchange.CoinbaseExchangeImpl;
import com.coinbase.exchange.api.exchange.Signature;
import com.coinbase.exchange.api.gui.frame.GuiFrame;
import com.coinbase.exchange.api.gui.liveorderbook.view.OrderBookView;
import com.coinbase.exchange.api.marketdata.MarketDataService;
import com.coinbase.exchange.api.websocketfeed.WebsocketFeed;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class DesktopConfiguration {

    @Bean
    public CoinbaseExchange coinbasePro(@Value("${exchange.key}") String publicKey,
                                        @Value("${exchange.passphrase}") String passphrase,
                                        @Value("${exchange.api.baseUrl}") String baseUrl,
                                        Signature signature) {
        return new CoinbaseExchangeImpl(publicKey, passphrase, baseUrl, signature);
    }

    @Bean
    public Signature signature(@Value("${exchange.secret}") String secret) {
        return new Signature(secret);
    }

    @Bean
    public WebsocketFeed websocketFeed(@Value("${websocket.baseUrl}") String websocketUrl,
                                       @Value("${exchange.key}") String apiKey,
                                       @Value("${exchange.passphrase}") String passphrase,
                                       @Value("${gui.enabled}") boolean guiEnabled,
                                       Signature signature) {
        return new WebsocketFeed(websocketUrl,
                apiKey,
                passphrase,
                guiEnabled,
                signature);
    }

    @Bean
    public OrderBookView orderBookViewPanel(@Value("${gui.enabled}") boolean guiEnabled, CoinbaseExchange coinbasePro, WebsocketFeed websocketFeed) {
        MarketDataService marketDataService = new MarketDataService(coinbasePro);
        return new OrderBookView(guiEnabled, marketDataService, websocketFeed);
    }

    @Bean
    public GuiFrame gui(@Value("${gui.enabled}") boolean guiEnabled, OrderBookView orderBookViewPanel) {
        return new GuiFrame(guiEnabled, orderBookViewPanel);
    }

}
