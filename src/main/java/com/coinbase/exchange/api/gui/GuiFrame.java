package com.coinbase.exchange.api.gui;


import org.springframework.stereotype.Component;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.util.concurrent.ExecutorService;

/**
 * Created by robevansuk on 10/03/2017.
 */
@Component
public class GuiFrame extends JFrame {

    static ExecutorService exe;

    public GuiFrame() {
        super("GDAX desktop client");
        setupDisplayLayout();
    }

    private void setupDisplayLayout() {
        this.setLayout(new BorderLayout());
        this.setSize(640, 480);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }


}
