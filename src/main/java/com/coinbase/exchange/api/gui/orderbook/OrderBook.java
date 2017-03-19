package com.coinbase.exchange.api.gui.orderbook;

import com.coinbase.exchange.api.marketdata.MarketData;
import com.coinbase.exchange.api.marketdata.MarketDataService;
import com.coinbase.exchange.api.websocketfeed.WebsocketFeed;
import com.coinbase.exchange.api.websocketfeed.message.HeartBeat;
import com.coinbase.exchange.api.websocketfeed.message.OrderReceivedOrderBookMessage;
import com.coinbase.exchange.api.websocketfeed.message.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by robevansuk on 10/03/2017.
 */
@Component
public class OrderBook extends JPanel {

    static final Logger log = LoggerFactory.getLogger(OrderBook.class);
    static String[] productIds = new String[] { "BTC-GBP" }; // make this configurable.

    WebsocketFeed websocketFeed;
    MarketDataService marketDataService;

    Map<String, JTable> orderBookTables;
    Map<String, JPanel> productOrderBooks;
    private boolean isAlive;

    Map<BigDecimal, Integer> bidPriceIndex;
    Map<BigDecimal, Integer> askPriceIndex;

    List<BigDecimal[]> bids;
    List<BigDecimal[]> asks;


    @Autowired
    public OrderBook(MarketDataService marketDataService, WebsocketFeed websocketFeed) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.websocketFeed = websocketFeed;
        this.marketDataService = marketDataService;
        this.orderBookTables = new HashMap<>();
        this.productOrderBooks = new HashMap<>();
        this.askPriceIndex = new HashMap<>();
        this.bidPriceIndex = new HashMap<>();
    }

    public void load(){
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    for (String productId : productIds) {
                        log.info("******** Getting the orderbook for: " + productId);
                        MarketData marketData = marketDataService.getMarketDataOrderBook(productId, "2");

                        bids = new LinkedList<BigDecimal[]>(Arrays.asList(marketData.getBids()));// marketData.getBids();
                        asks = new LinkedList<BigDecimal[]>(Arrays.asList(marketData.getBids()));//marketData.getAsks();

                        JPanel panel = new JPanel();
                        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                        initPriceIndex(bidPriceIndex, bids);
                        initPriceIndex(askPriceIndex, asks);

                        JTable bidTable = initJList(bids);
                        orderBookTables.put(productId + "_bids", bidTable);
                        JTable askTable = initJList(asks);
                        orderBookTables.put(productId + "_asks", askTable);

                        panel.add(bidTable);
                        panel.add(askTable);

                        productOrderBooks.put(productId, panel);

                    }
                }
            });
        } catch (InterruptedException | InvocationTargetException ex){

        }

        // start live feed(s)
        log.info("******** Opening Websocket Feed");
        websocketFeed.subscribe(new Subscribe(productIds), this);
        this.add(productOrderBooks.get(productIds[0]));
    }

    private void initPriceIndex(Map<BigDecimal, Integer> priceIndex, List<BigDecimal[]> list) {
        int i = 0;
        for(BigDecimal[] item : list) {
            //log.info("Item: " + item[1] + ", index: " + i);
            priceIndex.put(item[1], i);
            i++;
        }
    }

    private JTable initJList(List<BigDecimal[]> marketData) {
        DefaultTableModel model = new DefaultTableModel();
        for (BigDecimal[] entry : marketData) {
            model.addRow(entry);
        }

        return new JTable(model);
    }

    /**
     * fire an updated to this panel so the view updates.
     */
    public void callRepaint() {
       SwingUtilities.invokeLater(new Runnable() {
           @Override
           public void run() {
               repaint();
           }
       });
    }


    public void heartBeat(HeartBeat object) {
        isAlive = true;
    }

    public void updateOrderBookReceived(OrderReceivedOrderBookMessage msg) {
        BigDecimal newSize = new BigDecimal(0);
        DefaultTableModel model =
                (DefaultTableModel) orderBookTables.get(msg.getProduct_id()+"_"
                        +(msg.getSide().equals("buy")?"bids":"asks")).getModel();

        Integer rowId = getRowId(msg.getPrice());
        if (rowId != null) {
            BigDecimal currentSize = (BigDecimal) model.getValueAt(rowId, 0);
            newSize = currentSize.add(msg.getSize());
            model.setValueAt(newSize, rowId, 0);
        } else {

            BigDecimal topAsk = asks.get(0)[1];
            BigDecimal topBid = bids.get(0)[1];

            List<BigDecimal[]> insertionList;
            if (msg.getSize().equals("buy")) {
                 insertionList = bids;
            } else {
                insertionList = asks;
            }

            // TODO could improve this with a binary search algorithm for better insertion perf.
            for (int i=0; i < insertionList.size(); i++){
                if (insertionList.get(i)[1].compareTo(msg.getPrice()) == -1) {
                    BigDecimal[] newItem = { msg.getSize() , msg.getPrice(), new BigDecimal(1)};
                    model.insertRow(i, newItem);
                    break;
                }
            }
        }

    }

    public Integer getRowId(BigDecimal price){
        Integer rowIdFromAsks = askPriceIndex.get(price);
        Integer rowIdFromBids = bidPriceIndex.get(price);
        if (rowIdFromAsks != null) {
            return rowIdFromAsks;
        } else if (rowIdFromBids != null) {
            return rowIdFromBids;
        } else {
            return null;
        }
    }


    public void updateOrderBook(OrderReceivedOrderBookMessage order) {
        updateOrderBookReceived(order);
    }
}
