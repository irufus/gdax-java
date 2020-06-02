package com.coinbase.exchange.gui.liveorderbook;

import com.coinbase.exchange.websocketfeed.OrderBookMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.coinbase.exchange.gui.liveorderbook.OrderBookConstants.ORDER_QTY_COLUMN;
import static com.coinbase.exchange.gui.liveorderbook.OrderBookConstants.PRICE_COLUMN;
import static com.coinbase.exchange.gui.liveorderbook.OrderBookConstants.SIZE_COLUMN;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by robevansuk on 31/03/2017.
 */
public class OrderBookModelTest {

    OrderBookModel testObject;
    public static final int FIRST_ROW = 0;

    @BeforeEach
    public void setup() {
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
    public void shouldUpdateExistingRowWhenPricePointExists() {
        // given
        addEntry(FIRST_ROW, "1.0", "1.0", "1");

        assertEquals(1, testObject.getRowCount());
        assertRowValues(FIRST_ROW, "1.0", "1.0", "1");

        // when
        testObject.insertInto(new OrderBookMessage.OrderBookMessageBuilder()
                .setType("limit")
                .setSide("buy")
                .setPrice(new BigDecimal(1.5))
                .setSize(new BigDecimal(3.0)).build());

        // then
        assertEquals(2, testObject.getRowCount());
        assertRowValues(FIRST_ROW, "1.50000", "3.00000", "1");

        // when
        testObject.insertInto(new OrderBookMessage.OrderBookMessageBuilder()
                .setType("limit")
                .setSide("buy")
                .setPrice(new BigDecimal(1.5))
                .setSize(new BigDecimal(2.200)).build());

        // then
        assertEquals(2, testObject.getRowCount());
        assertRowValues(FIRST_ROW, "1.50000", "5.20000", "2");
    }

    @Test
    public void shouldInsertBuyOrderAsNewRowWhenPriceIsUnique() {
        testObject.setValueAt("1.0", 0, 0);

        String testResult = ((String) testObject.getValueAt(0, 0));
        assertEquals("1.0", testResult);

        testObject.insertInto(new OrderBookMessage.OrderBookMessageBuilder()
                .setType("limit")
                .setSide("buy")
                .setPrice(new BigDecimal(1.5))
                .setSize(new BigDecimal(3.0)).build());

        assertRowValues(FIRST_ROW, "1.50000", "3.00000", "1");

        testObject.insertInto(new OrderBookMessage.OrderBookMessageBuilder()
                .setType("limit")
                .setSide("buy")
                .setPrice(new BigDecimal("3.8"))
                .setSize(new BigDecimal("0.43400")).build());

        // item should appear at the top of the list since it's a new highest bidder/buy order
        assertRowValues(FIRST_ROW, "3.80000", "0.43400", "1");
    }

    @Test
    public void shouldInsertSellOrderAsNewRowWhenPriceIsUnique() {
        testObject.setValueAt("1.00000", 0, 0);

        String testResult = ((String) testObject.getValueAt(0, 0));

        assertEquals("1.00000", testResult);

        OrderBookMessage message = new OrderBookMessage.OrderBookMessageBuilder()
                .setPrice(new BigDecimal("3.8"))
                .setSize(new BigDecimal("0.43400"))
                .setSide("sell").build();

        testObject.insertInto(message);

        // item should appear at the top of the list since it's a new highest bidder/buy order
        assertRowValues(FIRST_ROW, "3.80000", "0.43400", "1");
    }

    @Test
    public void shouldUpdateExistingSellOrderWhenPriceIsNotUnique() {
        addEntry(FIRST_ROW, "1.00000", "0.87655", "1");

        // create a new order
        OrderBookMessage message = new OrderBookMessage.OrderBookMessageBuilder()
                .setPrice(new BigDecimal("1.0"))
                .setSize(new BigDecimal("0.43400"))
                .setSide("sell").build();

        // insert the new order
        testObject.insertInto(message);

        // item should appear at the top of the list since it's a new highest bidder/buy order
        assertEquals(1, testObject.getRowCount());
        assertRowValues(FIRST_ROW, "1.00000", "1.31055", "2");
    }

    @Test
    public void shouldUpdateExistingBuyOrderWhenPricePointAlreadyExistsInDataTable() {
        addEntry(FIRST_ROW, "1.00000", "0.87655", "1");

        // create a new order
        OrderBookMessage message = new OrderBookMessage.OrderBookMessageBuilder()
                .setPrice(new BigDecimal("1.0"))
                .setSize(new BigDecimal("0.43400"))
                .setSide("buy").build();

        // insert the new order
        testObject.insertInto(message);

        // item should appear at the top of the list since it's a new highest bidder/buy order
        assertEquals(1, testObject.getRowCount());
        assertRowValues(FIRST_ROW, "1.00000", "1.31055", "2");
    }

    @Test
    public void shouldReduceQtyByOneForDoneOrder() {
        addEntry(FIRST_ROW, "1.00000", "0.87655", "5");

        // create a new order
        OrderBookMessage message = new OrderBookMessage.OrderBookMessageBuilder()
                .setType("done")
                .setSide("buy")
                .setPrice(new BigDecimal("1.0"))
                .setSize(new BigDecimal("0.43400")).build();

        // insert the new order
        testObject.insertInto(message);

        // item should appear at the top of the list since it's a new highest bidder/buy order
        assertEquals(1, testObject.getRowCount());
        assertRowValues(FIRST_ROW, "1.00000", "0.44255", "4");
    }

    @Test
    public void shouldReduceQtyByOneForMatchedOrder() {
        addEntry(FIRST_ROW, "1.00000", "0.87655", "5");

        assertEquals(1, testObject.getRowCount());
        assertRowValues(FIRST_ROW, "1.00000", "0.87655", "5");

        // create a new order
        OrderBookMessage message = new OrderBookMessage.OrderBookMessageBuilder()
                .setType("matched")
                .setPrice(new BigDecimal("1.0"))
                .setSize(new BigDecimal("0.43400"))
                .setSide("buy").build();

        // insert the new order
        testObject.insertInto(message);

        // item should appear at the top of the list since it's a new highest bidder/buy order
        assertEquals(1, testObject.getRowCount());
        assertRowValues(FIRST_ROW, "1.00000", "0.44255", "4");
    }

    @Test
    public void shouldReduceQtyByOneForMatchedOrderWithRemainingSize() {
        addEntry(FIRST_ROW, "1.00000", "0.87655", "5");
        assertRowValues(FIRST_ROW, "1.00000", "0.87655", "5");

        // create a new order
        OrderBookMessage message = new OrderBookMessage.OrderBookMessageBuilder()
                .setType("matched")
                .setPrice(new BigDecimal("1.0"))
                .setRemainingSize(new BigDecimal("0.43400"))
                .setSide("buy").build();

        // insert the new order
        testObject.insertInto(message);

        // item should appear at the top of the list since it's a new highest bidder/buy order
        assertRowValues(FIRST_ROW, "1.00000", "0.43400", "4");
    }


    @Test
    public void shouldReduceQtyByOneForDoneOrderWithRemainingSize() {
        addEntry(FIRST_ROW, "1.00000", "0.87655", "5");

        assertRowValues(FIRST_ROW, "1.00000", "0.87655", "5");

        // create a new order
        OrderBookMessage message = new OrderBookMessage.OrderBookMessageBuilder()
                .setType("done")
                .setSide("buy")
                .setPrice(new BigDecimal("1.0"))
                .setRemainingSize(new BigDecimal("0.43400")).build();

        // insert the new order
        testObject.insertInto(message);

        // item should appear at the top of the list since it's a new highest bidder/buy order
        assertRowValues(FIRST_ROW, "1.00000", "0.43400", "4");
    }

    @Test
    public void shouldAddItemToLastOrdersList() {
        // create a new order
        OrderBookMessage message = new OrderBookMessage.OrderBookMessageBuilder()
                .setType("done")
                .setSide("buy")
                .setPrice(new BigDecimal("1.0"))
                .setRemainingSize(new BigDecimal("0.43400"))
                .setSequence(1L).build();

        testObject.insertSequencedMessage(message,0);

        assertEquals(1, testObject.getReceivedOrders().size());
    }

    @Test
    public void shouldInsertOrderBookMessageAfterExistingOrder() {
        // create a new order
        OrderBookMessage message = new OrderBookMessage.OrderBookMessageBuilder()
                .setType("done")
                .setPrice(new BigDecimal("1.0"))
                .setRemainingSize(new BigDecimal("0.43400"))
                .setSide("buy")
                .setSequence(1L).build();

        testObject.insertSequencedMessage(message,0);

        assertEquals(1, testObject.getReceivedOrders().size());

        OrderBookMessage newMessage = new OrderBookMessage();
        newMessage.setSequence(2L);

        testObject.insertSequencedMessage(newMessage,1);

        assertEquals(2, testObject.getReceivedOrders().size());
        assertEquals(1L, testObject.getReceivedOrders().get(0).getSequence());
        assertEquals(2L, testObject.getReceivedOrders().get(1).getSequence());
    }

    @Test
    public void shouldInsertOrderBookMessageBeforeExistingOrder() {
        // create a new order
        OrderBookMessage message = new OrderBookMessage.OrderBookMessageBuilder()
                .setType("done")
                .setPrice(new BigDecimal(1.0))
                .setRemainingSize(new BigDecimal("0.43400"))
                .setSide("buy")
                .setSequence(1L).build();

        testObject.insertSequencedMessage(message,0);

        assertEquals(1, testObject.getReceivedOrders().size());

        OrderBookMessage newMessage = new OrderBookMessage.OrderBookMessageBuilder().setSequence(0L).build();

        testObject.insertSequencedMessage(newMessage,0);

        assertEquals(2, testObject.getReceivedOrders().size());
        assertEquals(0L, testObject.getReceivedOrders().get(0).getSequence());
        assertEquals(1L, testObject.getReceivedOrders().get(1).getSequence());
    }

    @Test
    public void shouldInsertOneOrderIntoTable() {
        // create a new order
        OrderBookMessage message = new OrderBookMessage.OrderBookMessageBuilder()
                .setType("limit")
                .setPrice(new BigDecimal("1.0"))
                .setRemainingSize(new BigDecimal("0.43400"))
                .setSide("buy")
                .setSequence(1L).build();

        testObject.incomingOrder(message);

        assertEquals(1, testObject.getRowCount());
        assertEquals(1L, testObject.getReceivedOrders().get(0).getSequence());
    }

    @Test
    public void shouldInsertTwoOrdersIntoTable() {
        // create a new order
        OrderBookMessage message1 = new OrderBookMessage.OrderBookMessageBuilder()
                .setType("limit")
                .setPrice(new BigDecimal("1.0"))
                .setRemainingSize(new BigDecimal("0.43400"))
                .setSide("buy")
                .setSequence(1L).build();

        testObject.incomingOrder(message1);

        assertEquals(1, testObject.getRowCount());
        assertEquals(1L, testObject.getReceivedOrders().get(0).getSequence());

        OrderBookMessage message2 = new OrderBookMessage.OrderBookMessageBuilder()
                .setType("limit")
                .setPrice(new BigDecimal("1.1"))
                .setRemainingSize(new BigDecimal("0.43400"))
                .setSide("buy")
                .setSequence(2L).build();

        testObject.incomingOrder(message2);

        assertEquals(2, testObject.getRowCount());
        assertEquals(1L, testObject.getReceivedOrders().get(0).getSequence());
        assertEquals(2L, testObject.getReceivedOrders().get(1).getSequence());
    }

    @Test
    public void shouldInsertTwoOrdersIntoTableAfterMissingMessageIsReceived() {
        // create a new order
        OrderBookMessage message1 = new OrderBookMessage.OrderBookMessageBuilder()
                .setType("limit")
                .setPrice(new BigDecimal("1.0"))
                .setRemainingSize(new BigDecimal("0.43400"))
                .setSide("buy")
                .setSequence(1L).build();

        testObject.incomingOrder(message1);

        assertEquals(1, testObject.getRowCount());
        assertEquals(1L, testObject.getReceivedOrders().get(0).getSequence());

        // third message will be received before 2nd message
        OrderBookMessage message3 = new OrderBookMessage.OrderBookMessageBuilder()
                .setType("limit")
                .setPrice(new BigDecimal("1.1"))
                .setRemainingSize(new BigDecimal("0.43400"))
                .setSide("buy")
                .setSequence(3L).build();

        testObject.incomingOrder(message3);

        assertEquals(2, testObject.getRowCount());
        assertEquals(1L, testObject.getReceivedOrders().get(0).getSequence());
        assertEquals(3L, testObject.getReceivedOrders().get(1).getSequence());

        // 2nd message arrived late
        OrderBookMessage message2 = new OrderBookMessage.OrderBookMessageBuilder()
                .setType("limit")
                .setPrice(new BigDecimal("1.3"))
                .setRemainingSize(new BigDecimal("0.43400"))
                .setSide("buy")
                .setSequence(2L).build();

        testObject.incomingOrder(message2);

        assertEquals(3, testObject.getRowCount());
        assertEquals(1L, testObject.getReceivedOrders().get(0).getSequence());
        assertEquals(2L, testObject.getReceivedOrders().get(1).getSequence());
        assertEquals(3L, testObject.getReceivedOrders().get(2).getSequence());
    }

    @Test
    public void shouldUpdatePriceForOpenOrder() {
        testObject.insertInto(new OrderBookMessage.OrderBookMessageBuilder()
                .setType("open")
                .setSide("buy")
                .setPrice(new BigDecimal(2.0))
                .setSize(new BigDecimal(3.0))
                .setRemainingSize(new BigDecimal(1.5)) // open orders depend on remaining size not size
                .build());

        assertRowValues(FIRST_ROW, "2.00000", "1.50000", "1");
    }

    private void assertRowValues(int rowIndex, String price, String size, String qty) {
        assertEquals(price, ((String) testObject.getValueAt(rowIndex, PRICE_COLUMN)));
        assertEquals(size, ((String) testObject.getValueAt(rowIndex, SIZE_COLUMN)));
        assertEquals(qty, ((String) testObject.getValueAt(rowIndex, ORDER_QTY_COLUMN)));
    }

    private void addEntry(int rowIndex, String price, String size, String qty) {
        testObject.setValueAt(price, rowIndex, PRICE_COLUMN);
        testObject.setValueAt(size, rowIndex, SIZE_COLUMN);
        testObject.setValueAt(qty, rowIndex, ORDER_QTY_COLUMN);
    }
}