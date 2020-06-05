package com.coinbase.exchange.gui.liveorderbook;

import com.coinbase.exchange.api.marketdata.OrderItem;
import com.coinbase.exchange.websocketfeed.OrderBookMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import static com.coinbase.exchange.api.marketdata.MessageEX.MessageType.DONE;
import static com.coinbase.exchange.api.marketdata.MessageEX.MessageType.MATCH;
import static com.coinbase.exchange.gui.liveorderbook.OrderBookConstants.ORDER_QTY_COLUMN;
import static com.coinbase.exchange.gui.liveorderbook.OrderBookConstants.PRICE_COLUMN;
import static com.coinbase.exchange.gui.liveorderbook.OrderBookConstants.SIZE_COLUMN;
import static java.util.stream.Collectors.toList;


public class OrderBookModel implements TableModel, TableModelListener {

    static final Logger log = LoggerFactory.getLogger(OrderBookModel.class);

    private static final String CANCELED = "canceled";
    private static final String INVERT = "invert";

    private static final int PRICE_DECIMAL_PLACES = 5;
    private static final int SIZE_DECIMAL_PLACES = 8;

    private static final Comparator<OrderBookMessage> priceComparator = (o1, o2) -> o1.getPrice().compareTo(o2.getPrice()) * -1;
    private static final Comparator<OrderBookMessage> sequenceComparator = (o1, o2) -> o2.getSequence().compareTo(o1.getSequence()) * -1;

    public Vector<Vector> tableData;

    private static final String[] columnNames = {
            "price",
            "size",
            "#orders"
    };

    List<OrderBookMessage> sequentiallyOrderedMessages;
    Map<Long, OrderBookMessage> historicOrdersMap;
    Map<OrderBookMessage, Integer> priceIndexMap;

    public OrderBookModel() {
        tableData = new Vector<>();
        addTableModelListener(this);
        this.sequentiallyOrderedMessages = new LinkedList<>();
        this.historicOrdersMap = new HashMap<>();
        this.priceIndexMap = new HashMap<>();
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
        return tableData.size();
    }


    public Object getValueAt(int rowIndex, int columnIndex) {
        return tableData.get(rowIndex).get(columnIndex);
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (rowIndex >= getRowCount()) {
            tableData.add(emptyEntry());
        }

        if (aValue instanceof String) {
            tableData.get(rowIndex).set(columnIndex, (String) aValue);
        }
        if (aValue instanceof BigDecimal) {
            tableData.get(rowIndex).set(columnIndex, getPriceAsString((BigDecimal) aValue));
        }

        fireAllChanged();
    }

    public void removeRow(int index) {
        tableData.remove(index);
    }

    public void tableChanged(TableModelEvent e) {
        if (isInsertORUpdateORDeleteOrderType(e)) {
                fireAllChanged();
        }
    }

    private boolean isInsertORUpdateORDeleteOrderType(TableModelEvent e) {
        return e.getType() == TableModelEvent.DELETE
            || e.getType() == TableModelEvent.INSERT
            ||  e.getType() == TableModelEvent.UPDATE;
    }

    protected void fireAllChanged() {
        TableModelEvent e = new TableModelEvent(this);
        fireTableModelEvent(e);
    }

    public void insertRowAt(OrderItem item, int rowIndex) {

        Vector emptyEntry = emptyEntry();

        tableData.insertElementAt(emptyEntry, rowIndex);

        setValueAt(item.getPrice().setScale(PRICE_DECIMAL_PLACES, RoundingMode.HALF_UP).toString(), rowIndex, PRICE_COLUMN);
        setValueAt(item.getSize().setScale(SIZE_DECIMAL_PLACES, RoundingMode.HALF_UP).toString(), rowIndex, SIZE_COLUMN);
        setValueAt(item.getNum().toString(), rowIndex, ORDER_QTY_COLUMN);

        fireAllChanged();
    }

    private Vector emptyEntry() {
        Vector emptyEntry = new Vector(3);
        emptyEntry.add("");
        emptyEntry.add("");
        emptyEntry.add("");
        return emptyEntry;
    }

    public BigDecimal getOrderSize(OrderBookMessage msg) {
        BigDecimal result = new BigDecimal(0);
        if (msg.getSize() != null) {
            result = msg.getSize();
        }
        if (msg.getRemaining_size() != null){
            result = msg.getRemaining_size();
        }
        return result.setScale(SIZE_DECIMAL_PLACES, RoundingMode.HALF_UP);
    }

    public BigDecimal getOrderPrice(OrderBookMessage msg) {
        return msg.getPrice().setScale(PRICE_DECIMAL_PLACES, RoundingMode.HALF_UP);
    }

    /**
     * TODO
     * Using a binary search for every order that comes in is in-efficient when applied to all orders
     * Convert to lookup historic orders in a map
     * @param msg
     * @return
     */
    public void insertSequencedMessage(OrderBookMessage msg, int sequenceEntryIndex) {
        if (sequenceEntryIndex < 0) {
            // message did not exist in historicOrders so negative index for
            // the insertion point was returned. ADD new item at inverted index point
            sequenceEntryIndex = invertIndex(sequenceEntryIndex);
            sequentiallyOrderedMessages.add(sequenceEntryIndex, msg);//new entry
        } else {
            log.warn("Sequence number already seen {}: {}", sequenceEntryIndex, msg);
        }
    }

    private Integer getEntryIndex(List listToSearch, OrderBookMessage msg, Comparator comparator) {
        // check the index to insert the order at.
//        if (!historicOrdersMap.containsKey(msg.getSequence())) {
//            historicOrdersMap.put(msg.getSequence(), msg);
//        } else {
//            priceIndexMap.get(msg);
//        }
        return Collections.binarySearch(listToSearch, msg, comparator);
    }

    public int insertInto(OrderBookMessage msg) {

        // take a list of all prices on the current live orderbook.
        int priceEntryIndex = getEntryIndex(livePriceEntriesList(msg), msg, priceComparator);

        // only insert new row entry into table data if its at a new price point or the insertion point is 0 and the table is empty.
        if (priceEntryIndex < 0) {
            // item did not exist so negative index for the insertion point was returned
            // insert item at this point
            priceEntryIndex = invertIndex(priceEntryIndex);
            log.info("Inserting order at NEW price point {}: {}, {}, {}", priceEntryIndex, getOrderPrice(msg), msg.toString(), msg.getReason());
            insertNewPricePoint(priceEntryIndex, msg);

        } else if (priceEntryIndex == 0 && getRowCount() == 0) {
            insertNewPricePoint(priceEntryIndex, msg);
        }

        // get the qty and size values ready to update at the insertion/update index
        Integer currentQty = Integer.parseInt((String)getValueAt(priceEntryIndex, ORDER_QTY_COLUMN));
        BigDecimal currentSize = new BigDecimal((String)getValueAt(priceEntryIndex, SIZE_COLUMN));

        BigDecimal newSize = getNewOrderSize(msg, currentSize);
        Integer newQty = getNewQuantity(msg.getType(), currentQty);

        setValueAt(newSize, priceEntryIndex, SIZE_COLUMN);
        setValueAt(newQty + "", priceEntryIndex, ORDER_QTY_COLUMN);

        validateOrderBookElseRemoveRow(priceEntryIndex);

        return priceEntryIndex;
    }

    private List<OrderBookMessage> livePriceEntriesList(OrderBookMessage msg) {
        return tableData.stream()
                .map(tableRow ->
                    new OrderBookMessage.OrderBookMessageBuilder()
                               .setPrice(getPriceAsBigDecimal((String)tableRow.get(PRICE_COLUMN)))
                               .setSide(msg.getSide()).build())
                .collect(toList());
    }

    private BigDecimal getNewOrderSize(OrderBookMessage msg, BigDecimal currentSize) {
        if (msg.getType() != null) {
            if (msg.getType().equals(DONE) || msg.getType().equals(MATCH) || msg.getType().equals(INVERT)) {

                if (msg.getReason() !=null && msg.getReason().equals(CANCELED)) {
                     return BigDecimal.ZERO;
                }
                if (msg.getRemaining_size() != null) {
                    return msg.getRemaining_size();
                } else {
                    if (msg.getSize() != null) {
                        return getOrderSize(msg).negate().add(currentSize); // subtract if we have one of the order types above
                    } else {
                        return BigDecimal.ZERO;
                    }
                }

            } else {
                if (msg.getRemaining_size() != null){
                    return msg.getRemaining_size();
                } else {
                    if (msg.getSize() != null) {
                        return msg.getSize().add(currentSize); // add for orders that are not done, matched, invert.
                    }
                    return new BigDecimal(0);
                }
            }
        } else {
            if (msg.getRemaining_size() != null){
               return msg.getRemaining_size();
            } else {
               return getOrderSize(msg).add(currentSize);
            }
        }
    }

    private int getNewQuantity(String type, int currentQty) {
        if (type != null) {
            if (type.equals(DONE) || type.equals(MATCH) || type.equals(INVERT)) {
                return currentQty - 1;
            } else {
                return currentQty + 1;
            }
        } else {
            return currentQty + 1;
        }
    }

    private int invertIndex(int index) {
        return (index * -1) - 1;
    }

    private void insertNewPricePoint(int index, OrderBookMessage message) {
        if (index >= getRowCount()
                || !(tableData.get(index).get(PRICE_COLUMN)).equals(getOrderPrice(message))){
            tableData.add(index, emptyEntry());
        }
        setValueAt(getPriceAsString(message.getPrice()), index, PRICE_COLUMN);
        setValueAt("0", index, SIZE_COLUMN);
        setValueAt("0", index, ORDER_QTY_COLUMN);
    }

    public List<OrderBookMessage> getSequentiallyOrderedMessages(){
        return sequentiallyOrderedMessages;
    }

    private void validateOrderBookElseRemoveRow(int rowUpdated) {
        BigDecimal currentPrice = getPriceAsBigDecimal((String)getValueAt(rowUpdated, SIZE_COLUMN));
        Integer currentQty = Integer.parseInt((String)getValueAt(rowUpdated, ORDER_QTY_COLUMN));
        if (currentPrice.compareTo(new BigDecimal(0)) <= 0 || currentQty <= 0) {
            removeRow(rowUpdated);
        }
    }

    private BigDecimal getPriceAsBigDecimal(String priceString) {
        return new BigDecimal(priceString).setScale(PRICE_DECIMAL_PLACES, RoundingMode.HALF_UP);
    }

    private String getPriceAsString(BigDecimal bigDecimal) {
        return bigDecimal.setScale(PRICE_DECIMAL_PLACES, RoundingMode.HALF_UP).toString();
    }

    public void incomingOrder(OrderBookMessage msg) {
        // Checks if the price point already exists
        int sequenceIndex = getEntryIndex(sequentiallyOrderedMessages, msg, sequenceComparator);

        insertSequencedMessage(msg, sequenceIndex);

        applyOrdersFrom(sequenceIndex);
    }

    /**
     * apply the inverse of all but the seqIndex to the end of the list, then reapply all orders in sequence to remain in sync
     **/
    public void applyOrdersFrom(int index) {

        if (index < 0) {
            index = invertIndex(index);
        }

        int historicOrdersLastElementId = sequentiallyOrderedMessages.size() - 1;
        //log.warn("Replaying orders from {}, index: {} of {}",  receivedOrders.get(index).getSequence(), index+1, historicOrdersLastElementId+1);

        // undo all orders applied after this one
        if (index != historicOrdersLastElementId) {
            undoOrdersToIndexExclusive(index, historicOrdersLastElementId);
        }

        // re apply all orders following this one
        applyOrdersFromIndexInclusive(index, historicOrdersLastElementId);
    }

    private void applyOrdersFromIndexInclusive(int index, int historicOrdersLastElementId) {
        for (int i = index; i <= historicOrdersLastElementId; i++){
//            log.info("Applying order {}, {} of {}", receivedOrders.get(i).getSequence(), i+1, historicOrdersLastElementId+1);
            insertInto(sequentiallyOrderedMessages.get(i));
        }
    }

    private void undoOrdersToIndexExclusive(int index, int lastIndex) {
        for (int i = lastIndex; i > index; i--) {
//            log.warn("Inverting order {}, {} of {}", receivedOrders.get(i).getSequence(), i+1, lastIndex+1);
            insertInto(invertOrder(sequentiallyOrderedMessages.get(i)));
        }
    }

    private OrderBookMessage invertOrder(OrderBookMessage orderBookMessage) {
        OrderBookMessage invertOrder = new OrderBookMessage();
        invertOrder.setType("invert");
        invertOrder.setSize(getOrderSize(orderBookMessage).negate());
        invertOrder.setPrice(getOrderPrice(orderBookMessage));
        return invertOrder;
    }
}