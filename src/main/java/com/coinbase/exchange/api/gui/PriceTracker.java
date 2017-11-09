package com.coinbase.exchange.api.gui;

import com.coinbase.exchange.api.GdaxApiApplication;
import com.coinbase.exchange.api.marketdata.MarketDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;

import javax.swing.*;
import java.awt.*;

import static com.coinbase.exchange.api.GdaxApiApplication.SYSTEM_PROPERTY_JAVA_AWT_HEADLESS;

/**
 * Created by robevans.uk on 01/09/2017.
 */
public class PriceTracker {

    static final Logger log = LoggerFactory.getLogger(PriceTracker.class);

    Boolean guiEnabled;

    JFrame frame;

    MarketDataService marketDataService;

    JLabel prices;

    @Autowired
    public PriceTracker(@Value("${gui.enabled}") boolean guiEnabled, MarketDataService marketDataService) {
        this.guiEnabled = guiEnabled;
        this.marketDataService = marketDataService;
        if (guiEnabled) {
            startGui();
        }
    }

    public void startGui() {
        if (guiEnabled) {
            frame = new JFrame("Gdax Desktop Client");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(640, 120);
            frame.setLayout(new BorderLayout());
            frame.setVisible(true);
            prices = new JLabel("BTC: $0.00 | ETH: $0.00 | LTC: $0.00");
            frame.add(prices);
            log.info("JFrame CTOR");
        }

        SwingUtilities.invokeLater(() -> {
            while(true) {
                String btcPrice = marketDataService.getMarketDataOrderBook("BTC-USD", "1").getAsks().get(0).getPrice().toString();
                String ethPrice = marketDataService.getMarketDataOrderBook("ETH-USD", "1").getAsks().get(0).getPrice().toString();
                String ltcPrice = marketDataService.getMarketDataOrderBook("LTC-USD", "1").getAsks().get(0).getPrice().toString();
                prices.setText(String.format("BTC: ${} | ETH: ${} | LTC: ${}", btcPrice, ethPrice, ltcPrice));
            }
        });
    }

    public static void main(String[] args) {
        System.setProperty(SYSTEM_PROPERTY_JAVA_AWT_HEADLESS, System.getProperty(SYSTEM_PROPERTY_JAVA_AWT_HEADLESS, Boolean.toString(false)));
        SpringApplication.run(GdaxApiApplication.class, args);
    }
}
