package com.coinbase.exchange.api.websocketfeed.message;

public class Channel {
    private String name;
    private String[] product_ids;

    public Channel() {}

    public Channel(String name) {
        this.name = name;
    }

    public Channel(String name, String[] product_ids) {
        this.name = name;
        this.product_ids = product_ids;
    }

    public String getName() {
        return name;
    }

    public String[] getProduct_ids() {
        return product_ids;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProduct_ids(String[] product_ids) {
        this.product_ids = product_ids;
    }
}
