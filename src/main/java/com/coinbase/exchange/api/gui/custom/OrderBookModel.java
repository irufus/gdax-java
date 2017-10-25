package com.coinbase.exchange.api.gui.custom;

import com.coinbase.exchange.api.marketdata.OrderItem;
import com.coinbase.exchange.api.websocketfeed.message.OrderBookMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.math.BigDecimal;
import java.util.*;

import static java.util.stream.Collectors.toList;


public class OrderBookModel implements TableModel, TableModelListener {

    static final Logger log = LoggerFactory.getLogger(OrderBookModel.class);
    static final int PRICE_DECIMAL_PLACES = 5;
    static final int SIZE_DECIMAL_PLACES = 8;

    public Vector<Vector> data;

    private static String[] columnNames = {
            "price",
            "size",
            "#orders"
    };

    List<OrderBookMessage> lastOrders;

    public OrderBookModel() {
        data = new Vector<>();
        addTableModelListener(this);
        this.lastOrders = new LinkedList<>();
    }

    EventListenerList listenerList = new EventListenerList();

    // listener stuff
    public void addTableModelListener(TableModelListener l) {
        listenerList.add(TableModelListener.class, l);
    }

    public void removeTableModelListener(TableModelListener l) {
        listenerList.remove(TableModelListener.class, l);
    }

    public void fireTableModelEvent(TableModelEvent e) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 1; i > 0; i--) {
            if (listeners[i] == TableModelListener.class) {
                ((TableModelListener) listeners[i + 1]).tableChanged(e);
            }
        }
    }

    // contents stuff

    public Class getColumnClass(int columnIndex) {
        if (getRowCount() > 0)
            return getValueAt(0, columnIndex).getClass();
        else
            return Object.class;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public String getColumnName(int index) {
        return columnNames[index];
    }

    public int getRowCount() {
        return data.size();
    }


    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex).get(columnIndex);
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (rowIndex >= getRowCount()) {
            data.add(new Vector(3));
            for (int i = 0; i < 3; i++) {
                data.get(rowIndex).add("");
            }
        }

        if (aValue instanceof String) {
            data.get(rowIndex).set(columnIndex, (String) aValue);
        }

        fireAllChanged();
    }

    public void removeRow(int index) {
        data.remove(index);
    }

    public void tableChanged(TableModelEvent e) {
        switch (e.getType()) {
            case TableModelEvent.DELETE: {

                fireAllChanged();
                break;
            }
            case TableModelEvent.INSERT: {

                fireAllChanged();
                break;
            }
            case TableModelEvent.UPDATE: {

                fireAllChanged();
                break;
            }
        }
    }

    protected void fireAllChanged() {
        TableModelEvent e = new TableModelEvent(this);
        fireTableModelEvent(e);
    }

    public void insertRowAt(OrderItem item, int rowIndex) {

        Vector vector = new Vector(3);
        for (int i = 0; i < 3; i++) {
            vector.add("");
        }

        data.insertElementAt(vector, rowIndex);

        setValueAt(item.getPrice().setScale(PRICE_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP).toString(), rowIndex, 0);
        setValueAt(item.getSize().setScale(SIZE_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP).toString(), rowIndex, 1);
        setValueAt(item.getNum().toString(), rowIndex, 2);

        fireAllChanged();
    }

    public BigDecimal getOrderSize(OrderBookMessage msg) {
        BigDecimal result = new BigDecimal(0);
        if (msg.getSize() != null) {
            result = msg.getSize();
        }
        if (msg.getRemaining_size()!=null){
            result = msg.getRemaining_size();
        }
        return result.setScale(SIZE_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getOrderPrice(OrderBookMessage msg) {
        return msg.getPrice().setScale(PRICE_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP);
    }

    public int checkSequence(OrderBookMessage msg) {

        Comparator<OrderBookMessage> comparator = new Comparator<OrderBookMessage>(){
            @Override
            public int compare(OrderBookMessage o1, OrderBookMessage o2) {
                return o1.getSequence().compareTo(o2.getSequence());
            }
        };

        int index = Collections.binarySearch(lastOrders, msg, comparator);

        if (index <0) {
            // item did not exist so negative index for the insertion point was returned
            // insert item at this point
            index = (index+1) * -1;
            lastOrders.add(index, msg);
        } else if (index == 0) {
            lastOrders.add(index, msg);
        } else {
            log.error("Sequence number already seen {}: {}", index, msg);
        }
        return index;
    }

    public int insertInto(OrderBookMessage msg) {

        List<OrderBookMessage> orderIndex = data.stream()
                .map(w -> {
                    OrderBookMessage message = new OrderBookMessage();
                    message.setPrice(getPriceAsBigDecimal((String)w.get(0)));
                    message.setSide(msg.getSide());
                    return message;
                })
                .collect(toList());

        Comparator<OrderBookMessage> comparator = new Comparator<OrderBookMessage>(){
            @Override
            public int compare(OrderBookMessage o1, OrderBookMessage o2) {
                return o1.getPrice().compareTo(o2.getPrice()) * -1; // reverse order by price
            }
        };

        int index = Collections.binarySearch(orderIndex, msg, comparator);

        if (index < 0) {
            // item did not exist so negative index for the insertion point was returned
            // insert item at this point
            log.info("Inserting order at new price point {}: {}, {}", index, getOrderPrice(msg), msg);
            index = (index * -1) - 1;
            insert(index, msg);

        } else if (index == 0) {
            if (getRowCount() == 0) {
                insert(index, msg);
            }
        }

        Integer currentQty = Integer.parseInt((String)getValueAt(index,2));
        BigDecimal currentSize = new BigDecimal((String)getValueAt(index,1));
        BigDecimal newOrderSize = new BigDecimal(0); // just an init

        int newQty = 0;
        if (msg.getType() != null) {
            if (msg.getType().equals("done") || msg.getType().equals("matched") || msg.getType().equals("invert")) {

                if (msg.getRemaining_size()!=null){
                    newOrderSize = msg.getRemaining_size();
                } else {
                    if (msg.getSize() != null) {
                        newOrderSize = getOrderSize(msg).negate().add(currentSize);
                    } else {
                        newOrderSize = new BigDecimal(0);
                    }
                }
                newQty = currentQty - 1;

            } else {
                if (msg.getRemaining_size()!=null){
                    newOrderSize = msg.getRemaining_size();
                } else {
                    if (msg.getSize()!=null) {
                        newOrderSize = msg.getSize().add(currentSize);
                    }
                }
                newQty = currentQty + 1;
            }
        } else {
            if (msg.getRemaining_size()!=null){
                newOrderSize = msg.getRemaining_size();
            } else {
                newOrderSize = getOrderSize(msg).add(currentSize);
            }
            newQty = currentQty + 1;
        }

        setValueAt(getOrderPrice(msg), index, 0);
        setValueAt(getPriceAsString(newOrderSize), index, 1);
        setValueAt(newQty + "", index, 2);

        validateOrderBookElseRemoveRow(index);

        return index;
    }

    private void insert(int index, OrderBookMessage message) {
        if (index >= getRowCount()
                || !(data.get(index).get(0)).equals(getOrderPrice(message))){
            data.add(index, new Vector(3));
            for (int i = 0; i < 3; i++) {
                data.get(index).add("");
            }
        }
        setValueAt(getPriceAsString(message.getPrice()), index, 0);
        setValueAt("0", index, 1);
        setValueAt("0", index, 2);
    }

    public List<OrderBookMessage> getLastOrders(){
        return lastOrders;
    }

    private void validateOrderBookElseRemoveRow(int rowUpdated) {
        BigDecimal currentPrice = getPriceAsBigDecimal((String)getValueAt(rowUpdated, 1));
        Integer currentQty = Integer.parseInt((String)getValueAt(rowUpdated, 2));
        if (currentPrice.compareTo(new BigDecimal(0)) <= 0
                || currentQty <= 0) {
            removeRow(rowUpdated);
        }
    }

    private BigDecimal getPriceAsBigDecimal(OrderBookMessage msg) {
        return msg.getPrice().setScale(PRICE_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP);
    }

    private BigDecimal getSizeAsBigDecimal(OrderBookMessage msg) {
        return msg.getSize().setScale(SIZE_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP);
    }

    private BigDecimal getPriceAsBigDecimal(String priceString) {
        return new BigDecimal(priceString).setScale(PRICE_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP);
    }

    private BigDecimal getSizeAsBigDecimal(String sizeString) {
        return new BigDecimal(sizeString).setScale(SIZE_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP);
    }

    private String getPriceAsString(BigDecimal bigDecimal) {
        return bigDecimal.setScale(PRICE_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP).toString();
    }

    private String getSizeAsString(BigDecimal bigDecimal) {
        return bigDecimal.setScale(SIZE_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP).toString();
    }

    // apply the inverse of all but the seqIndex to the end of the list.
    public void replayFrom(int index) {

        log.warn("Replaying orders from {}, index: {} of {}",  lastOrders.get(index).getSequence(), index, lastOrders.size()-1);

        // undo all orders beyond this one
        for (int i=lastOrders.size()-1; i>index; i--){
            log.warn("Inverting order {}, {} of {}", lastOrders.get(i).getSequence(), i, lastOrders.size()-1);
            insertInto(invertOrder(lastOrders.get(i)));
        }

        // re apply all orders following this one
        for (int i=index; i<lastOrders.size(); i++){
            log.info("Applying order {}, {} of {}", lastOrders.get(i).getSequence(), i, lastOrders.size()-1);
            insertInto(lastOrders.get(i));
        }
    }

    private OrderBookMessage invertOrder(OrderBookMessage orderBookMessage) {
        OrderBookMessage invertOrder = new OrderBookMessage();
        invertOrder.setType("invert");
        invertOrder.setSize(getOrderSize(orderBookMessage).negate());
        invertOrder.setPrice(getOrderPrice(orderBookMessage));
        return invertOrder;
    }

    public void incomingOrder(OrderBookMessage msg) {
        int i = checkSequence(msg);
        replayFrom(i);
    }
}