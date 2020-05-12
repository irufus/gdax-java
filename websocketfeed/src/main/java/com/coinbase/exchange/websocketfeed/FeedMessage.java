package com.coinbase.exchange.websocketfeed;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.Instant;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ErrorOrderBookMessage.class, name = "error"),
        @JsonSubTypes.Type(value = SubscriptionsMessage.class, name = "subscriptions"),
        @JsonSubTypes.Type(value = HeartBeat.class, name = "heartbeat"),
        @JsonSubTypes.Type(value = ChangedOrderBookMessage.class, name = "change"),
        @JsonSubTypes.Type(value = DoneOrderBookMessage.class, name = "done"),
        @JsonSubTypes.Type(value = MatchedOrderBookMessage.class, name = "match"),
        @JsonSubTypes.Type(value = MatchedOrderBookMessage.class, name = "last_match"),
        @JsonSubTypes.Type(value = OpenedOrderBookMessage.class, name = "open"),
        @JsonSubTypes.Type(value = ReceivedOrderBookMessage.class, name = "received"),
        @JsonSubTypes.Type(value = TickerMessage.class, name = "ticker"),
        @JsonSubTypes.Type(value = ActivateOrderBookMessage.class, name = "activate"),
        @JsonSubTypes.Type(value = StatusMessage.class, name = "status"),
        @JsonSubTypes.Type(value = SnapshotMessage.class, name = "snapshot"),
        @JsonSubTypes.Type(value = L2UpdateMessage.class, name = "l2update"),
})
public abstract class FeedMessage {
    private String type;  // "received" | "open" | "done" | "match" | "change" | "activate"
    private Long sequence;
    private Instant time;
    private String product_id;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

}
