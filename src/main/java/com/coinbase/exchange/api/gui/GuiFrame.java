package com.coinbase.exchange.api.gui;


import com.coinbase.exchange.api.gui.orderbook.OrderBookView;
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

    OrderBookView orderBook;
    Boolean guiEnabled;

    JFrame frame;

    @Autowired
    public GuiFrame(@Value("${gui.enabled}") boolean guiEnabled, OrderBookView orderBook) {
            this.orderBook = orderBook;
            this.guiEnabled = guiEnabled;
        if (guiEnabled) {
            startGui();
        }
    }

    public void startGui() {
        if (guiEnabled) {
            frame = new JFrame("Gdax Desktop Client");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(640, 480);
            frame.setLayout(new BorderLayout());
            frame.add(orderBook, BorderLayout.EAST);
            frame.setVisible(true);
            log.info("JFrame CTOR");
        }
    }
}
