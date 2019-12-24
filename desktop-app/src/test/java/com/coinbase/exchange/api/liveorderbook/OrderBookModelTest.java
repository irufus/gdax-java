package com.coinbase.exchange.api.liveorderbook;

import com.coinbase.exchange.api.gui.liveorderbook.OrderBookModel;
import com.coinbase.exchange.api.websocketfeed.message.OrderBookMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Created by robevansuk on 31/03/2017.
 */
public class OrderBookModelTest {

    OrderBookModel testObject;

    @BeforeEach
    public void setup(){
        testObject = new OrderBookModel();
    }

    @Test
    public void shouldAddItemToTable() {
        testObject.setValueAt("Some Value", 0, 0);
        String testResult = ((String) testObject.getValueAt(0, 0));
        assertEquals("Some Value", testResult);
        assertEquals(1, testObject.getRowCount());
    }

    @Test
    public void shouldUpdateExistingRowWhenPricePointExists(){
        testObject.setValueAt("1.0", 0, 0);
        testObject.setValueAt("1.0", 0, 1);
        testObject.setValueAt("1", 0, 2);

        String testResult = ((String)testObject.getValueAt(0,0));

        assertEquals(1, testObject.getRowCount());
        assertEquals("1.0", testResult);
        OrderBookMessage message1 = new OrderBookMessage();
        message1.setType("limit");
        message1.setSide("buy");
        message1.setPrice(new BigDecimal(1.5));
        message1.setSize(new BigDecimal(3.0));

        testObject.insertInto(message1);

        int firstRow = 0;
        assertEquals(2, testObject.getRowCount());
        assertEquals("1.50000", testObject.getValueAt(firstRow, 0));
        assertEquals("3.00000", testObject.getValueAt(firstRow, 1));
        assertEquals("1", testObject.getValueAt(firstRow, 2));
        OrderBookMessage message2 = new OrderBookMessage();
        message2.setType("limit");
        message2.setSide("buy");
        message2.setPrice(new BigDecimal(1.5));
        message2.setSize(new BigDecimal(2.200));

        testObject.insertInto(message2);

        assertEquals(2, testObject.getRowCount());
        assertEquals("1.50000", testObject.getValueAt(firstRow, 0));
        assertEquals("5.20000",testObject.getValueAt(firstRow, 1));
        assertEquals("2", testObject.getValueAt(firstRow, 2));
    }

    @Test
    public void shouldInsertBuyOrderAsNewRowWhenPriceIsUnique(){
        testObject.setValueAt("1.0", 0, 0);

        String testResult = ((String)testObject.getValueAt(0,0));

       assertEquals("1.0", testResult);
       assertEquals(1, testObject.getRowCount());
        OrderBookMessage message1 = new OrderBookMessage();
        message1.setType("limit");
        message1.setSide("buy");
        message1.setPrice(new BigDecimal(1.5));
        message1.setSize(new BigDecimal(3.0));

        testObject.insertInto(message1);

        int firstRow = 0;
        assertEquals("1.50000", testObject.getValueAt(firstRow, 0));
        assertEquals("3.00000", testObject.getValueAt(firstRow, 1));
        assertEquals("1", testObject.getValueAt(firstRow, 2));

        OrderBookMessage message2 = new OrderBookMessage();
        message2.setPrice(new BigDecimal("3.8"));
        message2.setSize(new BigDecimal("0.43400"));
        message2.setSide("buy");
        message2.setType("limit");

        testObject.insertInto(message2);

        // item should appear at the top of the list since it's a new highest bidder/buy order
        assertEquals("3.80000", testObject.getValueAt(0, 0));
        assertEquals("0.43400", testObject.getValueAt(0, 1));
        assertEquals("1",  testObject.getValueAt(0, 2));
    }

    @Test
    public void shouldInsertSellOrderAsNewRowWhenPriceIsUnique(){
        testObject.setValueAt("1.00000", 0, 0);

        String testResult = ((String)testObject.getValueAt(0,0));

        assertEquals("1.00000", testResult);
        assertEquals(1, testObject.getRowCount());

        OrderBookMessage message = new OrderBookMessage();
        message.setPrice(new BigDecimal("3.8"));
        message.setSize(new BigDecimal("0.43400"));
        message.setSide("sell");

        testObject.insertInto(message);

        int firstRow = 0;

        // item should appear at the top of the list since it's a new highest bidder/buy order
        assertEquals("3.80000", testObject.getValueAt(firstRow, 0));
        assertEquals("0.43400", testObject.getValueAt(firstRow, 1));
        assertEquals("1",  testObject.getValueAt(firstRow, 2));
    }

    @Test
    public void shouldUpdateExistingSellOrderWhenPriceIsNotUnique(){
        int firstRow = 0;
        testObject.setValueAt("1.00000", firstRow, 0);
        testObject.setValueAt("0.87655",firstRow, 1);
        testObject.setValueAt("1",firstRow, 2);
        assertEquals("1.00000", ((String)testObject.getValueAt(firstRow, 0)));
        assertEquals("0.87655", ((String)testObject.getValueAt(firstRow, 1)));
        assertEquals("1", ((String)testObject.getValueAt(firstRow, 2)));
        assertEquals(1, testObject.getRowCount());
        // create a new order
        OrderBookMessage message = new OrderBookMessage();
        message.setPrice(new BigDecimal("1.0"));
        message.setSize(new BigDecimal("0.43400"));
        message.setSide("sell");

        // insert the new order
        testObject.insertInto(message);

        // item should appear at the top of the list since it's a new highest bidder/buy order
        assertEquals(1, testObject.getRowCount());
        assertEquals("1.00000", testObject.getValueAt(0, 0));
        assertEquals("1.31055", testObject.getValueAt(0, 1));
        assertEquals("2", testObject.getValueAt(0, 2));
    }

    @Test
    public void shouldUpdateExistingBuyOrderWhenPriceIsNotUnique(){
        int firstRow = 0;
        testObject.setValueAt("1.00000", firstRow, 0);
        testObject.setValueAt("0.87655",firstRow, 1);
        testObject.setValueAt("1",firstRow, 2);
        assertEquals("1.00000", ((String)testObject.getValueAt(firstRow, 0)));
        assertEquals("0.87655", ((String)testObject.getValueAt(firstRow, 1)));
        assertEquals("1", ((String)testObject.getValueAt(firstRow, 2)));
        assertEquals(1, testObject.getRowCount());

        // create a new order
        OrderBookMessage message = new OrderBookMessage();
        message.setPrice(new BigDecimal("1.0"));
        message.setSize(new BigDecimal("0.43400"));
        message.setSide("buy");

        // insert the new order
        testObject.insertInto(message);

        // item should appear at the top of the list since it's a new highest bidder/buy order
        assertEquals(1, testObject.getRowCount());
        assertEquals("1.00000", testObject.getValueAt(0, 0));
        assertEquals("1.31055", testObject.getValueAt(0, 1));
        assertEquals("2", testObject.getValueAt(0, 2));
    }

    @Test
    public void shouldReduceQtyByOneForDoneOrder(){
        int firstRow = 0;
        testObject.setValueAt("1.00000", firstRow, 0);
        testObject.setValueAt("0.87655",firstRow, 1);
        testObject.setValueAt("5",firstRow, 2);
        assertEquals("1.00000", ((String)testObject.getValueAt(firstRow, 0)));
        assertEquals("0.87655", ((String)testObject.getValueAt(firstRow, 1)));
        assertEquals("5", ((String)testObject.getValueAt(firstRow, 2)));
        assertEquals(1, testObject.getRowCount());

        // create a new order
        OrderBookMessage message = new OrderBookMessage();
        message.setType("done");
        message.setPrice(new BigDecimal("1.0"));
        message.setSize(new BigDecimal("0.43400"));
        message.setSide("buy");

        // insert the new order
        testObject.insertInto(message);
        // item should appear at the top of the list since it's a new highest bidder/buy order
        assertEquals("1.00000", testObject.getValueAt(0, 0));
        assertEquals("0.44255", testObject.getValueAt(0, 1));
        assertEquals("4", testObject.getValueAt(0, 2));
        assertEquals(1, testObject.getRowCount());
    }

    @Test
    public void shouldReduceQtyByOneForMatchedOrder(){
        int firstRow = 0;

        testObject.setValueAt("1.00000", firstRow, 0);
        testObject.setValueAt("0.87655",firstRow, 1);
        testObject.setValueAt("5",firstRow, 2);

        assertEquals("1.00000", ((String)testObject.getValueAt(firstRow, 0)));
        assertEquals("0.87655", ((String)testObject.getValueAt(firstRow, 1)));
        assertEquals("5", ((String)testObject.getValueAt(firstRow, 2)));
        assertEquals(1, testObject.getRowCount());

        // create a new order
        OrderBookMessage message = new OrderBookMessage();
        message.setType("matched");
        message.setPrice(new BigDecimal("1.0"));
        message.setSize(new BigDecimal("0.43400"));
        message.setSide("buy");

        // insert the new order
        testObject.insertInto(message);

        // item should appear at the top of the list since it's a new highest bidder/buy order
        assertEquals(1, testObject.getRowCount());
        assertEquals("1.00000", testObject.getValueAt(0, 0));
        assertEquals("0.44255", testObject.getValueAt(0, 1));
        assertEquals("4", testObject.getValueAt(0, 2));
    }

    @Test
    public void shouldReduceQtyByOneForMatchedOrderWithRemainingSize(){
        int firstRow = 0;
        testObject.setValueAt("1.00000", firstRow, 0);
        testObject.setValueAt("0.87655",firstRow, 1);
        testObject.setValueAt("5",firstRow, 2);
        assertEquals("1.00000", ((String)testObject.getValueAt(firstRow, 0)));
        assertEquals("0.87655", ((String)testObject.getValueAt(firstRow, 1)));
        assertEquals("5", ((String)testObject.getValueAt(firstRow, 2)));
        assertEquals(1, testObject.getRowCount());

        // create a new order
        OrderBookMessage message = new OrderBookMessage();
        message.setType("matched");
        message.setPrice(new BigDecimal("1.0"));
        message.setRemaining_size(new BigDecimal("0.43400"));
        message.setSide("buy");

        // insert the new order
        testObject.insertInto(message);

        // item should appear at the top of the list since it's a new highest bidder/buy order
        assertEquals(1, testObject.getRowCount());
        assertEquals("1.00000", testObject.getValueAt(0, 0));
        assertEquals("0.43400", testObject.getValueAt(0, 1));
        assertEquals("4", testObject.getValueAt(0, 2));
    }

    @Test
    public void shouldReduceQtyByOneForDoneOrderWithRemainingSize(){
        int firstRow = 0;

        testObject.setValueAt("1.00000", firstRow, 0);
        testObject.setValueAt("0.87655",firstRow, 1);
        testObject.setValueAt("5",firstRow, 2);

        assertEquals("1.00000", ((String)testObject.getValueAt(firstRow, 0)));
        assertEquals("0.87655", ((String)testObject.getValueAt(firstRow, 1)));
        assertEquals("5", ((String)testObject.getValueAt(firstRow, 2)));
        assertEquals(1, testObject.getRowCount());

        // create a new order
        OrderBookMessage message = new OrderBookMessage();
        message.setType("done");
        message.setPrice(new BigDecimal("1.0"));
        message.setRemaining_size(new BigDecimal("0.43400"));
        message.setSide("buy");

        // insert the new order
        testObject.insertInto(message);

        // item should appear at the top of the list since it's a new highest bidder/buy order
        assertEquals(1, testObject.getRowCount());
        assertEquals("1.00000", testObject.getValueAt(0, 0));
        assertEquals("0.43400", testObject.getValueAt(0, 1));
        assertEquals("4", testObject.getValueAt(0, 2));
    }

    @Test
    public void shouldAddItemToLastOrdersList(){
        // create a new order
        OrderBookMessage message = new OrderBookMessage();
        message.setType("done");
        message.setPrice(new BigDecimal("1.0"));
        message.setRemaining_size(new BigDecimal("0.43400"));
        message.setSide("buy");
        message.setSequence(1L);

        testObject.checkSequence(message);

        assertEquals(1, testObject.getLastOrders().size());
    }

    @Test
    public void shouldInsertOrderBookMessageAfterExistingOrder(){
        // create a new order
        OrderBookMessage message = new OrderBookMessage();
        message.setType("done");
        message.setPrice(new BigDecimal("1.0"));
        message.setRemaining_size(new BigDecimal("0.43400"));
        message.setSide("buy");
        message.setSequence(1L);

        testObject.checkSequence(message);

        assertEquals(1, testObject.getLastOrders().size());

        OrderBookMessage newMessage = new OrderBookMessage();
        newMessage.setSequence(2L);

        testObject.checkSequence(newMessage);

        assertEquals(2, testObject.getLastOrders().size());
        assertEquals(1L, testObject.getLastOrders().get(0).getSequence());
        assertEquals(2L, testObject.getLastOrders().get(1).getSequence());
    }

    @Test
    public void shouldInsertOrderBookMessageBeforeExistingOrder(){
        // create a new order
        OrderBookMessage message = new OrderBookMessage();
        message.setType("done");
        message.setPrice(new BigDecimal(1.0));
        message.setRemaining_size(new BigDecimal("0.43400"));
        message.setSide("buy");
        message.setSequence(1L);

        testObject.checkSequence(message);

        assertEquals(1, testObject.getLastOrders().size());

        OrderBookMessage newMessage = new OrderBookMessage();
        newMessage.setSequence(0L);

        testObject.checkSequence(newMessage);

        assertEquals(2, testObject.getLastOrders().size());
        assertEquals(0L, testObject.getLastOrders().get(0).getSequence());
        assertEquals(1L, testObject.getLastOrders().get(1).getSequence());
    }

    @Test
    public void shouldInsertOneOrderIntoTable(){
        // create a new order
        OrderBookMessage message = new OrderBookMessage();
        message.setType("limit");
        message.setPrice(new BigDecimal("1.0"));
        message.setRemaining_size(new BigDecimal("0.43400"));
        message.setSide("buy");
        message.setSequence(1L);

        testObject.incomingOrder(message);

        assertEquals(1, testObject.getRowCount());
        assertEquals(1L, testObject.getLastOrders().get(0).getSequence());
    }

    @Test
    public void shouldInsertTwoOrdersIntoTable(){
        // create a new order
        OrderBookMessage message1 = new OrderBookMessage();
        message1.setType("limit");
        message1.setPrice(new BigDecimal("1.0"));
        message1.setRemaining_size(new BigDecimal("0.43400"));
        message1.setSide("buy");
        message1.setSequence(1L);

        testObject.incomingOrder(message1);

        assertEquals(1, testObject.getRowCount());
        assertEquals(1L, testObject.getLastOrders().get(0).getSequence());

        OrderBookMessage message2 = new OrderBookMessage();
        message2.setType("limit");
        message2.setPrice(new BigDecimal("1.1"));
        message2.setRemaining_size(new BigDecimal("0.43400"));
        message2.setSide("buy");
        message2.setSequence(2L);

        testObject.incomingOrder(message2);

        assertEquals(2, testObject.getRowCount());
        assertEquals(1L, testObject.getLastOrders().get(0).getSequence());
        assertEquals(2L, testObject.getLastOrders().get(1).getSequence());
    }

    @Test
    public void shouldInsertTwoOrdersIntoTableAfterMissingMessageIsReceived(){
        // create a new order
        OrderBookMessage message1 = new OrderBookMessage();
        message1.setType("limit");
        message1.setPrice(new BigDecimal("1.0"));
        message1.setRemaining_size(new BigDecimal("0.43400"));
        message1.setSide("buy");
        message1.setSequence(1L);

        testObject.incomingOrder(message1);

        assertEquals(1, testObject.getRowCount());
        assertEquals(1L, testObject.getLastOrders().get(0).getSequence());

        // third message will be received before 2nd message
        OrderBookMessage message3 = new OrderBookMessage();
        message3.setType("limit");
        message3.setPrice(new BigDecimal("1.1"));
        message3.setRemaining_size(new BigDecimal("0.43400"));
        message3.setSide("buy");
        message3.setSequence(3L);

        testObject.incomingOrder(message3);

        assertEquals(2, testObject.getRowCount());
        assertEquals(1L, testObject.getLastOrders().get(0).getSequence());
        assertEquals(3L, testObject.getLastOrders().get(1).getSequence());

        // 2nd message arrived late...
        OrderBookMessage message2 = new OrderBookMessage();
        message2.setType("limit");
        message2.setPrice(new BigDecimal("1.3"));
        message2.setRemaining_size(new BigDecimal("0.43400"));
        message2.setSide("buy");
        message2.setSequence(2L);

        testObject.incomingOrder(message2);

        assertEquals(3, testObject.getRowCount());
        assertEquals(1L, testObject.getLastOrders().get(0).getSequence());
        assertEquals(2L, testObject.getLastOrders().get(1).getSequence());
        assertEquals(3L, testObject.getLastOrders().get(2).getSequence());
    }
}