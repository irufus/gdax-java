package com.coinbase.exchange.gui.liveorderbook;

import com.coinbase.exchange.websocketfeed.DoneOrderBookMessage;
import com.coinbase.exchange.websocketfeed.OpenedOrderBookMessage;
import com.coinbase.exchange.websocketfeed.OrderBookMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
    private static final int FIRST_ROW = 0;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
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

        assertEquals(1, testObject.getSequentiallyOrderedMessages().size());
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

        assertEquals(1, testObject.getSequentiallyOrderedMessages().size());

        OrderBookMessage newMessage = new OrderBookMessage();
        newMessage.setSequence(2L);

        testObject.insertSequencedMessage(newMessage,1);

        assertEquals(2, testObject.getSequentiallyOrderedMessages().size());
        assertEquals(1L, testObject.getSequentiallyOrderedMessages().get(0).getSequence());
        assertEquals(2L, testObject.getSequentiallyOrderedMessages().get(1).getSequence());
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

        assertEquals(1, testObject.getSequentiallyOrderedMessages().size());

        OrderBookMessage newMessage = new OrderBookMessage.OrderBookMessageBuilder().setSequence(0L).build();

        testObject.insertSequencedMessage(newMessage,0);

        assertEquals(2, testObject.getSequentiallyOrderedMessages().size());
        assertEquals(0L, testObject.getSequentiallyOrderedMessages().get(0).getSequence());
        assertEquals(1L, testObject.getSequentiallyOrderedMessages().get(1).getSequence());
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
        assertEquals(1L, testObject.getSequentiallyOrderedMessages().get(0).getSequence());
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
        assertEquals(1L, testObject.getSequentiallyOrderedMessages().get(0).getSequence());

        OrderBookMessage message2 = new OrderBookMessage.OrderBookMessageBuilder()
                .setType("limit")
                .setPrice(new BigDecimal("1.1"))
                .setRemainingSize(new BigDecimal("0.43400"))
                .setSide("buy")
                .setSequence(2L).build();

        testObject.incomingOrder(message2);

        assertEquals(2, testObject.getRowCount());
        assertEquals(1L, testObject.getSequentiallyOrderedMessages().get(0).getSequence());
        assertEquals(2L, testObject.getSequentiallyOrderedMessages().get(1).getSequence());
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
        assertEquals(1L, testObject.getSequentiallyOrderedMessages().get(0).getSequence());

        // third message will be received before 2nd message
        OrderBookMessage message3 = new OrderBookMessage.OrderBookMessageBuilder()
                .setType("limit")
                .setPrice(new BigDecimal("1.1"))
                .setRemainingSize(new BigDecimal("0.43400"))
                .setSide("buy")
                .setSequence(3L).build();

        testObject.incomingOrder(message3);

        assertEquals(2, testObject.getRowCount());
        assertEquals(1L, testObject.getSequentiallyOrderedMessages().get(0).getSequence());
        assertEquals(3L, testObject.getSequentiallyOrderedMessages().get(1).getSequence());

        // 2nd message arrived late
        OrderBookMessage message2 = new OrderBookMessage.OrderBookMessageBuilder()
                .setType("limit")
                .setPrice(new BigDecimal("1.3"))
                .setRemainingSize(new BigDecimal("0.43400"))
                .setSide("buy")
                .setSequence(2L).build();

        testObject.incomingOrder(message2);

        assertEquals(3, testObject.getRowCount());
        assertEquals(1L, testObject.getSequentiallyOrderedMessages().get(0).getSequence());
        assertEquals(2L, testObject.getSequentiallyOrderedMessages().get(1).getSequence());
        assertEquals(3L, testObject.getSequentiallyOrderedMessages().get(2).getSequence());
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

    /**
     * This test passed first time, however in reality this websocket feed sequence didn't result in a correct live orderbook
     * since some incoming messages were missed. This occurred because the full orderbook (market data (bids/asks)) was requested
     * but only incoming messages were applied, not *all* messages following the orderbook's sequence ID collected since being active.
     *
     * This test remains as a way to demonstrate how we can test real websocket feed scenarios when applied to the orderbook.
     * @throws JsonProcessingException
     */
    @Test
    void shouldRemoveDoneOrderFromOrderbook() throws JsonProcessingException {
        /**
         * Scenario - we start with market data for a given price point and sequence ID associated
         * with the MarketData bids/asks
         */
        //Insert new OrderItem row at index: 2751, price: 7639.06, size: 0.02678326, qty: 1, Sequence: 9299152159, Product: BTC-GBP
        // given
        testObject.insertInto(new OrderBookMessage.OrderBookMessageBuilder()
                                .setPrice(new BigDecimal(7639.06000))
                                .setSize(new BigDecimal(0.02678326))
                                .setSequence(9299152159L)
                                .setSide("sell")
                                .build());
        // then simulate real incoming orders from the websocket.
        OpenedOrderBookMessage openedOrderBookMessage = objectMapper.readValue("{\"type\":\"open\",\"side\":\"sell\",\"product_id\":\"BTC-GBP\",\"time\":\"2020-06-05T22:32:18.640005Z\",\"sequence\":9299153605,\"price\":\"7639.06\",\"order_id\":\"99c058b8-e85d-431e-becc-7990867bd542\",\"remaining_size\":\"0.24160571\"}", OpenedOrderBookMessage.class);
        DoneOrderBookMessage doneCancelledOrderBookMessage = objectMapper.readValue("{\"type\":\"done\",\"side\":\"sell\",\"product_id\":\"BTC-GBP\",\"time\":\"2020-06-05T22:32:19.792982Z\",\"sequence\":9299153673,\"order_id\":\"99c058b8-e85d-431e-becc-7990867bd542\",\"reason\":\"canceled\",\"price\":\"7639.06\",\"remaining_size\":\"0.24160571\"}", DoneOrderBookMessage.class);

        // when
        testObject.insertInto(openedOrderBookMessage);
        testObject.insertInto(doneCancelledOrderBookMessage);

        // then
        assertEquals(0, testObject.getRowCount());

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