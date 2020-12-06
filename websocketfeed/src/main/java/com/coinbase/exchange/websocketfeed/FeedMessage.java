package com.coinbase.exchange.websocketfeed;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @JsonProperty("type")
    private String type;  // "received" | "open" | "done" | "match" | "change" | "activate"
    @JsonProperty("sequence")
    private Long sequence;
    @JsonProperty("time")
    private Instant time;
    @JsonProperty("product_id")
    private String productId;
}
