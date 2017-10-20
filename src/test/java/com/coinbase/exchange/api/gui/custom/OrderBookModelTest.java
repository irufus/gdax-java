package com.coinbase.exchange.api.gui.custom;

import com.coinbase.exchange.api.websocketfeed.message.OrderBookMessage;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by robevansuk on 31/03/2017.
 */
public class OrderBookModelTest {

    OrderBookModel testObject;

    @Before
    public void setup(){
        testObject = new OrderBookModel();
    }

    @Test
    public void shouldAddItemToTable() {
        testObject.setValueAt("Some Value", 0, 0);
        String testResult = ((String) testObject.getValueAt(0, 0));
        assertThat(testResult, equalTo("Some Value"));
        assertThat(testObject.getRowCount(), equalTo(1));
    }

    @Test
    public void shouldUpdateExistingRowWhenPricePointExists(){
        testObject.setValueAt("1.0", 0, 0);
        testObject.setValueAt("1.0", 0, 1);
        testObject.setValueAt("1", 0, 2);
        String testResult = ((String)testObject.getValueAt(0,0));

        assertThat(testObject.getRowCount(), equalTo(1));
        assertThat(testResult, equalTo("1.0"));

        OrderBookMessage message1 = new OrderBookMessage();
        message1.setType("limit");
        message1.setSide("buy");
        message1.setPrice(new BigDecimal(1.5));
        message1.setSize(new BigDecimal(3.0));

        testObject.insertInto(message1);

        int firstRow = 0;
        assertThat(testObject.getRowCount(), equalTo(2));
        assertThat(testObject.getValueAt(firstRow, 0), equalTo("1.50000"));
        assertThat(testObject.getValueAt(firstRow, 1), equalTo("3.00000"));
        assertThat(testObject.getValueAt(firstRow, 2), equalTo("1"));

        OrderBookMessage message2 = new OrderBookMessage();
        message2.setType("limit");
        message2.setSide("buy");
        message2.setPrice(new BigDecimal(1.5));
        message2.setSize(new BigDecimal(2.200));
        testObject.insertInto(message2);

        assertThat(testObject.getRowCount(), equalTo(2));
        assertThat(testObject.getValueAt(firstRow, 0), equalTo("1.50000"));
        assertThat(testObject.getValueAt(firstRow, 1), equalTo("5.20000"));
        assertThat(testObject.getValueAt(firstRow, 2), equalTo("2"));
    }

    @Test
    public void shouldInsertBuyOrderAsNewRowWhenPriceIsUnique(){
        testObject.setValueAt("1.0", 0, 0);
        String testResult = ((String)testObject.getValueAt(0,0));
        assertThat(testResult, equalTo("1.0"));
        assertThat(testObject.getRowCount(), equalTo(1));

        OrderBookMessage message1 = new OrderBookMessage();
        message1.setType("limit");
        message1.setSide("buy");
        message1.setPrice(new BigDecimal(1.5));
        message1.setSize(new BigDecimal(3.0));

        testObject.insertInto(message1);

        int firstRow = 0;
        assertThat(testObject.getValueAt(firstRow, 0), equalTo("1.50000"));
        assertThat(testObject.getValueAt(firstRow, 1), equalTo("3.00000"));
        assertThat(testObject.getValueAt(firstRow, 2), equalTo("1"));

        OrderBookMessage message2 = new OrderBookMessage();
        message2.setPrice(new BigDecimal("3.8"));
        message2.setSize(new BigDecimal("0.43400"));
        message2.setSide("buy");
        message2.setType("limit");

        testObject.insertInto(message2);

        // item should appear at the top of the list since it's a new highest bidder/buy order
        assertThat(testObject.getValueAt(0, 0), equalTo("3.80000"));
        assertThat(testObject.getValueAt(0, 1), equalTo("0.43400"));
        assertThat(testObject.getValueAt(0, 2), equalTo("1"));
    }

    @Test
    public void shouldInsertSellOrderAsNewRowWhenPriceIsUnique(){
        testObject.setValueAt("1.00000", 0, 0);
        String testResult = ((String)testObject.getValueAt(0,0));
        assertThat(testResult, equalTo("1.00000"));
        assertThat(testObject.getRowCount(), equalTo(1));

        OrderBookMessage message = new OrderBookMessage();
        message.setPrice(new BigDecimal("3.8"));
        message.setSize(new BigDecimal("0.43400"));
        message.setSide("sell");

        testObject.insertInto(message);

        int firstRow = 0;

        // item should appear at the top of the list since it's a new highest bidder/buy order
        assertThat(testObject.getValueAt(firstRow, 0), equalTo("3.80000"));
        assertThat(testObject.getValueAt(firstRow, 1), equalTo("0.43400"));
        assertThat(testObject.getValueAt(firstRow, 2), equalTo("1"));
    }

    @Test
    public void shouldUpdateExistingSellOrderWhenPriceIsNotUnique(){
        int firstRow = 0;
        testObject.setValueAt("1.00000", firstRow, 0);
        testObject.setValueAt("0.87655",firstRow, 1);
        testObject.setValueAt("1",firstRow, 2);
        assertThat(((String)testObject.getValueAt(firstRow, 0)), equalTo("1.00000"));
        assertThat(((String)testObject.getValueAt(firstRow, 1)), equalTo("0.87655"));
        assertThat(((String)testObject.getValueAt(firstRow, 2)), equalTo("1"));
        assertThat(testObject.getRowCount(), equalTo(1));

        // create a new order
        OrderBookMessage message = new OrderBookMessage();
        message.setPrice(new BigDecimal("1.0"));
        message.setSize(new BigDecimal("0.43400"));
        message.setSide("sell");

        // insert the new order
        testObject.insertInto(message);

        // item should appear at the top of the list since it's a new highest bidder/buy order
        assertThat(testObject.getRowCount(), equalTo(1));
        assertThat(testObject.getValueAt(0, 0), equalTo("1.00000"));
        assertThat(testObject.getValueAt(0, 1), equalTo("1.31055"));
        assertThat(testObject.getValueAt(0, 2), equalTo("2"));
    }

    @Test
    public void shouldUpdateExistingBuyOrderWhenPriceIsNotUnique(){
        int firstRow = 0;
        testObject.setValueAt("1.00000", firstRow, 0);
        testObject.setValueAt("0.87655",firstRow, 1);
        testObject.setValueAt("1",firstRow, 2);
        assertThat(((String)testObject.getValueAt(firstRow, 0)), equalTo("1.00000"));
        assertThat(((String)testObject.getValueAt(firstRow, 1)), equalTo("0.87655"));
        assertThat(((String)testObject.getValueAt(firstRow, 2)), equalTo("1"));
        assertThat(testObject.getRowCount(), equalTo(1));

        // create a new order
        OrderBookMessage message = new OrderBookMessage();
        message.setPrice(new BigDecimal("1.0"));
        message.setSize(new BigDecimal("0.43400"));
        message.setSide("buy");

        // insert the new order
        testObject.insertInto(message);
        int lastRow = testObject.getRowCount() - 1;
        // item should appear at the top of the list since it's a new highest bidder/buy order
        assertThat(testObject.getValueAt(0, 0), equalTo("1.00000"));
        assertThat(testObject.getValueAt(0, 1), equalTo("1.31055"));
        assertThat(testObject.getValueAt(0, 2), equalTo("2"));
        assertThat(testObject.getRowCount(), equalTo(1));
    }

    @Test
    public void shouldReduceQtyByOneForDoneOrder(){
        int firstRow = 0;
        testObject.setValueAt("1.00000", firstRow, 0);
        testObject.setValueAt("0.87655",firstRow, 1);
        testObject.setValueAt("5",firstRow, 2);
        assertThat(((String)testObject.getValueAt(firstRow, 0)), equalTo("1.00000"));
        assertThat(((String)testObject.getValueAt(firstRow, 1)), equalTo("0.87655"));
        assertThat(((String)testObject.getValueAt(firstRow, 2)), equalTo("5"));
        assertThat(testObject.getRowCount(), equalTo(1));

        // create a new order
        OrderBookMessage message = new OrderBookMessage();
        message.setType("done");
        message.setPrice(new BigDecimal("1.0"));
        message.setSize(new BigDecimal("0.43400"));
        message.setSide("buy");

        // insert the new order
        testObject.insertInto(message);
        // item should appear at the top of the list since it's a new highest bidder/buy order
        assertThat(testObject.getValueAt(0, 0), equalTo("1.00000"));
        assertThat(testObject.getValueAt(0, 1), equalTo("0.44255"));
        assertThat(testObject.getValueAt(0, 2), equalTo("4"));
        assertThat(testObject.getRowCount(), equalTo(1));
    }

    @Test
    public void shouldReduceQtyByOneForMatchedOrder(){
        int firstRow = 0;
        testObject.setValueAt("1.00000", firstRow, 0);
        testObject.setValueAt("0.87655",firstRow, 1);
        testObject.setValueAt("5",firstRow, 2);
        assertThat(((String)testObject.getValueAt(firstRow, 0)), equalTo("1.00000"));
        assertThat(((String)testObject.getValueAt(firstRow, 1)), equalTo("0.87655"));
        assertThat(((String)testObject.getValueAt(firstRow, 2)), equalTo("5"));
        assertThat(testObject.getRowCount(), equalTo(1));

        // create a new order
        OrderBookMessage message = new OrderBookMessage();
        message.setType("matched");
        message.setPrice(new BigDecimal("1.0"));
        message.setSize(new BigDecimal("0.43400"));
        message.setSide("buy");

        // insert the new order
        testObject.insertInto(message);

        // item should appear at the top of the list since it's a new highest bidder/buy order
        assertThat(testObject.getValueAt(0, 0), equalTo("1.00000"));
        assertThat(testObject.getValueAt(0, 1), equalTo("0.44255"));
        assertThat(testObject.getValueAt(0, 2), equalTo("4"));
        assertThat(testObject.getRowCount(), equalTo(1));
    }

    @Test
    public void shouldReduceQtyByOneForMatchedOrderWithRemainingSize(){
        int firstRow = 0;
        testObject.setValueAt("1.00000", firstRow, 0);
        testObject.setValueAt("0.87655",firstRow, 1);
        testObject.setValueAt("5",firstRow, 2);
        assertThat(((String)testObject.getValueAt(firstRow, 0)), equalTo("1.00000"));
        assertThat(((String)testObject.getValueAt(firstRow, 1)), equalTo("0.87655"));
        assertThat(((String)testObject.getValueAt(firstRow, 2)), equalTo("5"));
        assertThat(testObject.getRowCount(), equalTo(1));

        // create a new order
        OrderBookMessage message = new OrderBookMessage();
        message.setType("matched");
        message.setPrice(new BigDecimal("1.0"));
        message.setRemaining_size(new BigDecimal("0.43400"));
        message.setSide("buy");

        // insert the new order
        testObject.insertInto(message);
        // item should appear at the top of the list since it's a new highest bidder/buy order
        assertThat(testObject.getValueAt(0, 0), equalTo("1.00000"));
        assertThat(testObject.getValueAt(0, 1), equalTo("0.43400"));
        assertThat(testObject.getValueAt(0, 2), equalTo("4"));
        assertThat(testObject.getRowCount(), equalTo(1));
    }

    @Test
    public void shouldReduceQtyByOneForDoneOrderWithRemainingSize(){
        int firstRow = 0;
        testObject.setValueAt("1.00000", firstRow, 0);
        testObject.setValueAt("0.87655",firstRow, 1);
        testObject.setValueAt("5",firstRow, 2);
        assertThat(((String)testObject.getValueAt(firstRow, 0)), equalTo("1.00000"));
        assertThat(((String)testObject.getValueAt(firstRow, 1)), equalTo("0.87655"));
        assertThat(((String)testObject.getValueAt(firstRow, 2)), equalTo("5"));
        assertThat(testObject.getRowCount(), equalTo(1));

        // create a new order
        OrderBookMessage message = new OrderBookMessage();
        message.setType("done");
        message.setPrice(new BigDecimal("1.0"));
        message.setRemaining_size(new BigDecimal("0.43400"));
        message.setSide("buy");

        // insert the new order
        testObject.insertInto(message);
        // item should appear at the top of the list since it's a new highest bidder/buy order
        assertThat(testObject.getValueAt(0, 0), equalTo("1.00000"));
        assertThat(testObject.getValueAt(0, 1), equalTo("0.43400"));
        assertThat(testObject.getValueAt(0, 2), equalTo("4"));
        assertThat(testObject.getRowCount(), equalTo(1));
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

        assertThat(testObject.getLastOrders().size(), equalTo(1));
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

        assertThat(testObject.getLastOrders().size(), equalTo(1));

        OrderBookMessage newMessage = new OrderBookMessage();
        newMessage.setSequence(2L);

        testObject.checkSequence(newMessage);

        assertThat(testObject.getLastOrders().size(), equalTo(2));
        assertThat(testObject.getLastOrders().get(0).getSequence(), equalTo(1L));
        assertThat(testObject.getLastOrders().get(1).getSequence(), equalTo(2L));
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

        assertThat(testObject.getLastOrders().size(), equalTo(1));

        OrderBookMessage newMessage = new OrderBookMessage();
        newMessage.setSequence(0L);

        testObject.checkSequence(newMessage);

        assertThat(testObject.getLastOrders().size(), equalTo(2));
        assertThat(testObject.getLastOrders().get(0).getSequence(), equalTo(0L));
        assertThat(testObject.getLastOrders().get(1).getSequence(), equalTo(1L));
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

        assertThat(testObject.getRowCount(), equalTo(1));
        assertThat(testObject.getLastOrders().get(0).getSequence(), equalTo(1L));
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

        assertThat(testObject.getRowCount(), equalTo(1));
        assertThat(testObject.getLastOrders().get(0).getSequence(), equalTo(1L));

        OrderBookMessage message2 = new OrderBookMessage();
        message2.setType("limit");
        message2.setPrice(new BigDecimal("1.1"));
        message2.setRemaining_size(new BigDecimal("0.43400"));
        message2.setSide("buy");
        message2.setSequence(2L);

        testObject.incomingOrder(message2);

        assertThat(testObject.getRowCount(), equalTo(2));
        assertThat(testObject.getLastOrders().get(0).getSequence(), equalTo(1L));
        assertThat(testObject.getLastOrders().get(1).getSequence(), equalTo(2L));
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

        assertThat(testObject.getRowCount(), equalTo(1));
        assertThat(testObject.getLastOrders().get(0).getSequence(), equalTo(1L));

        // third message will be received before 2nd message
        OrderBookMessage message3 = new OrderBookMessage();
        message3.setType("limit");
        message3.setPrice(new BigDecimal("1.1"));
        message3.setRemaining_size(new BigDecimal("0.43400"));
        message3.setSide("buy");
        message3.setSequence(3L);

        testObject.incomingOrder(message3);

        assertThat(testObject.getRowCount(), equalTo(2));
        assertThat(testObject.getLastOrders().get(0).getSequence(), equalTo(1L));
        assertThat(testObject.getLastOrders().get(1).getSequence(), equalTo(3L));

        // 2nd message arrived late...
        OrderBookMessage message2 = new OrderBookMessage();
        message2.setType("limit");
        message2.setPrice(new BigDecimal("1.3"));
        message2.setRemaining_size(new BigDecimal("0.43400"));
        message2.setSide("buy");
        message2.setSequence(2L);

        testObject.incomingOrder(message2);

        assertThat(testObject.getRowCount(), equalTo(3));
        assertThat(testObject.getLastOrders().get(0).getSequence(), equalTo(1L));
        assertThat(testObject.getLastOrders().get(1).getSequence(), equalTo(2L));
        assertThat(testObject.getLastOrders().get(2).getSequence(), equalTo(3L));
    }
}