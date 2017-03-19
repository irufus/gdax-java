package com.coinbase.exchange.api.gui;


import com.coinbase.exchange.api.gui.orderbook.OrderBook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by robevansuk on 10/03/2017.
 */

@Component
public class GuiFrame {

    static final Logger log = LoggerFactory.getLogger(GuiFrame.class);

    OrderBook orderBook;

    JFrame frame;

    @Autowired
    public GuiFrame(@Value("${gui.enabled}") boolean guiEnabled, OrderBook orderBook) {
        log.info("********* Window Initialisation");
        if (guiEnabled) {
            init();
        }

        this.orderBook = orderBook;
        frame.add(orderBook, BorderLayout.EAST);

        log.info("Starting the order book...");
        orderBook.load();
    }

    public void init() {
        frame = new JFrame("Gdax Desktop Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(640, 480);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
    }
}
