package com.coinbase.exchange.gui.config;

import com.coinbase.exchange.api.exchange.CoinbaseExchange;
import com.coinbase.exchange.api.exchange.CoinbaseExchangeImpl;
import com.coinbase.exchange.api.exchange.Signature;
import com.coinbase.exchange.api.marketdata.MarketDataService;
import com.coinbase.exchange.api.products.ProductService;
import com.coinbase.exchange.gui.frame.GuiFrame;
import com.coinbase.exchange.gui.liveorderbook.view.OrderBookView;
import com.coinbase.exchange.gui.liveorderbook.view.OrderBookViewController;
import com.coinbase.exchange.gui.websocketfeed.WebsocketFeed;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class DesktopConfiguration {

    public static final String CURRENT_PRODUCT_SELECTED = "BTC-GBP";

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    @Bean
    public CoinbaseExchange coinbasePro(@Value("${exchange.key}") String publicKey,
                                        @Value("${exchange.passphrase}") String passphrase,
                                        @Value("${exchange.api.baseUrl}") String baseUrl,
                                        Signature signature,
                                        ObjectMapper objectMapper) {
        return new CoinbaseExchangeImpl(publicKey, passphrase, baseUrl, signature, objectMapper);
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
                                       Signature signature,
                                       ObjectMapper objectMapper) {
        return new WebsocketFeed(websocketUrl,
                apiKey,
                passphrase,
                guiEnabled,
                signature,
                objectMapper);
    }

    @Bean
    public OrderBookView orderBookViewPanel(@Value("${gui.enabled}") boolean guiEnabled,
                                            CoinbaseExchange coinbasePro,
                                            WebsocketFeed websocketFeed,
                                            ObjectMapper objectMapper) {
        MarketDataService marketDataService = new MarketDataService(coinbasePro);
        return new OrderBookView(guiEnabled,
                CURRENT_PRODUCT_SELECTED,
                marketDataService,
                websocketFeed,
                objectMapper);
    }

    @Bean
    public ProductService productService(CoinbaseExchange coinbaseExchange) {
        return new ProductService(coinbaseExchange);
    }

    @Bean
    public OrderBookViewController orderBookViewController(ProductService productService,
                                                           OrderBookView orderBookView) {
        return new OrderBookViewController(CURRENT_PRODUCT_SELECTED, productService, orderBookView);
    }

    @Bean
    public GuiFrame gui(@Value("${gui.enabled}") boolean guiEnabled,
                        OrderBookView orderBookView,
                        OrderBookViewController orderBookViewController) {
        return new GuiFrame(guiEnabled, orderBookView, orderBookViewController);
    }
}
