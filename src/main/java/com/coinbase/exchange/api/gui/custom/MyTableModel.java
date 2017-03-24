package com.coinbase.exchange.api.gui.custom;

import com.coinbase.exchange.api.marketdata.OrderItem;
import com.coinbase.exchange.api.websocketfeed.message.OrderBookMessage;
import com.coinbase.exchange.api.websocketfeed.message.OrderReceivedOrderBookMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.math.BigDecimal;
import java.util.Vector;


public class MyTableModel implements TableModel, TableModelListener {

    static final Logger log = LoggerFactory.getLogger(MyTableModel.class);

    public Vector<Vector> data;

    private static String[] columnNames = {
            "price",
            "size",
            "#orders"
    };

    public MyTableModel() {
        data = new Vector<>();
        addTableModelListener(this);
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
//        log.info("Get: " + rowIndex + ", " + columnIndex + ", Data dimensions: " + data.size() + ", " + data.get(rowIndex).size());
        return data.get(rowIndex).get(columnIndex);
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
//        log.info("TableModel.setValueAt(" + aValue + ", " + rowIndex + ", " + columnIndex + ")");

        if (rowIndex >= getRowCount()) {
            data.add(new Vector(3));
            for (int i=0; i<3; i++) {
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

    public void updateExistingRow(OrderBookMessage msg, int rowIndex) {
        BigDecimal currentOrderSize = new BigDecimal((String)getValueAt(rowIndex,1));
        BigDecimal newOrderSize = currentOrderSize.add(getOrderSize(msg));

        Integer newQty = Integer.parseInt((String)getValueAt(rowIndex,2)) + 1;
//        log.info("Updating " + msg.getSide() + "@" + getOrderPrice(msg) + " from " + currentOrderSize + " to " + newOrderSize + " on row " + rowIndex);

        setValueAt(newOrderSize.toString(), rowIndex, 1);
        setValueAt(newQty.toString(), rowIndex, 2);
    }

    public void insertRowAt(OrderBookMessage msg, int rowIndex) {
//        log.info("TableModel.insertRowAt Price: " + getOrderPrice(msg) + ", rowIndex: " + rowIndex);

        Vector vector = new Vector(3);
        for (int i=0; i<3; i++){
            vector.add("");
        }

        data.insertElementAt(vector, rowIndex);

        setValueAt(getOrderPrice(msg).toString(), rowIndex, 0);
        setValueAt(getOrderSize(msg).toString(), rowIndex, 1);
        setValueAt("1", rowIndex, 2);

        fireAllChanged();
    }

    public void insertRowAt(OrderItem item, int rowIndex) {
//        log.info("TableModel: InsertingRowAt(" + rowIndex + ")" + ", Price: " + item.getPrice());

        Vector vector = new Vector(3);
        for (int i=0; i<3; i++){
            vector.add("");
        }

        data.insertElementAt(vector, rowIndex);

        setValueAt(item.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString(), rowIndex, 0);
        setValueAt(item.getSize().setScale(5, BigDecimal.ROUND_HALF_UP).toString(), rowIndex, 1);
        setValueAt(item.getNum().toString(), rowIndex, 2);

        fireAllChanged();
    }


    public BigDecimal getOrderSize(OrderBookMessage msg) {
        return msg.getSize().setScale(5, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getOrderPrice(OrderBookMessage msg) {
        return msg.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void appendBottom(OrderReceivedOrderBookMessage msg) {
        insertRowAt(msg, getRowCount());
    }

    public void appendTop(OrderReceivedOrderBookMessage msg) {
        insertRowAt(msg, 0);
    }
}