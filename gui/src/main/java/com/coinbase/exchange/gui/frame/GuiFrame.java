package com.coinbase.exchange.gui.frame;

import com.coinbase.exchange.gui.liveorderbook.view.OrderBookPresentation;
import com.coinbase.exchange.gui.liveorderbook.view.OrderBookViewController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

/**
 * Created by robevansuk on 10/03/2017.
 */

public class GuiFrame {

    static final Logger log = LoggerFactory.getLogger(GuiFrame.class);

    private OrderBookPresentation orderBook;
    private Boolean guiEnabled;
    private OrderBookViewController orderBookViewController;

    JFrame frame;

    public GuiFrame(boolean guiEnabled,
                    OrderBookPresentation orderBook,
                    OrderBookViewController orderBookViewController) {

        this.guiEnabled = guiEnabled;
        if (guiEnabled) {
            this.orderBook = orderBook;
            this.orderBookViewController = orderBookViewController;
            startGui();
        }
    }

    public void startGui() {
        if (guiEnabled) {
            frame = new JFrame("Gdax Desktop Client");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(640, 480);
            frame.setLayout(new BorderLayout());
            // TODO fix the product selection drop down so that it displays correctly
            // frame.add(orderBookViewController, BorderLayout.WEST);
            frame.add(orderBook, BorderLayout.EAST);
            frame.setVisible(true);
            log.info("JFrame CTOR");
        }
    }

}
