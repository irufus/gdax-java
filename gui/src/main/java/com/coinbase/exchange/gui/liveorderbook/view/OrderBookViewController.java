package com.coinbase.exchange.gui.liveorderbook.view;

import com.coinbase.exchange.api.products.ProductService;
import com.coinbase.exchange.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.stream.Collectors.toList;

public class OrderBookViewController extends JPanel {

    public static final Logger log = LoggerFactory.getLogger(OrderBookViewController.class);

    private final List<Product> productIds;
    private final OrderBookPresentation orderbookView;

    private String currentProductSelection;
    private CompletableFuture<JPanel> futureProductsList;

    public OrderBookViewController(String currentProductSelected,
                                   ProductService productService,
                                   OrderBookPresentation orderbookPresentation) {
        super();
        this.productIds = productService.getProducts();
        this.orderbookView = orderbookPresentation;
        this.currentProductSelection = currentProductSelected;
        getProductsList();
    }

    public void getProductsList() {
        SwingWorker<Void , Void> backgroundTask = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                log.info("Getting available products");
                final String[] products = (String[]) productIds.stream().map(p -> p.getId()).collect(toList()).toArray();
                final JComboBox productSelectionList = new JComboBox(products);
                productSelectionList.setFont(new Font("Calibri", Font.PLAIN, 11));
                productSelectionList.addActionListener(actionEvent -> {
                    final String productId = actionEvent.getActionCommand();
                    if (!productId.equals(currentProductSelection)) {
                        orderbookView.switchProduct(productId);
                    }
                });
                addProductsDropDown(productSelectionList);
                return null;
            }
        };

        backgroundTask.execute();
    }

    private void addProductsDropDown(final JComboBox productSelectionList) {
        this.add(productSelectionList);
    }

    public OrderBookViewController get() {
        return null;
    }
}
