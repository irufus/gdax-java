package com.coinbase.exchange.api.gui;


import com.coinbase.exchange.api.gui.orderbook.OrderBook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

/**
 * Created by robevansuk on 10/03/2017.
 */

@Component
public class GuiFrame {

    static final Logger log = LoggerFactory.getLogger(GuiFrame.class);

    OrderBook orderBook;
    Boolean guiEnabled;

    JFrame frame;

    @Autowired
    public GuiFrame(@Value("${gui.enabled}") boolean guiEnabled, OrderBook orderBook) {
        this.orderBook = orderBook;
        this.guiEnabled = guiEnabled;
        startGui();
    }

    public void startGui() {
        // Main Gui should be created using invokeLater thread.
//        new Thread(() -> {
//            SwingUtilities.invokeLater(() -> {
                if (guiEnabled) {
                    frame = new JFrame("Gdax Desktop Client");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setSize(640, 480);
                    frame.setLayout(new BorderLayout());
                    frame.add(orderBook, BorderLayout.EAST);
                    frame.setVisible(true);
                    log.info("JFrame CTOR");

                }
//            });
//        }).start();
    }
}
