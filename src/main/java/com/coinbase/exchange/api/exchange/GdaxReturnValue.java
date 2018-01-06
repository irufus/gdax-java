package com.coinbase.exchange.api.exchange;

import java.util.List;
import java.util.Map;

/**
 * Created by test on 1/5/18.
 */
public class GdaxReturnValue {
    private String GdaxJson;
    private int code;
    private Map<String, List<String>> header;

    GdaxReturnValue(String value, int code, Map<String, List<String>> header) {
        this.GdaxJson = value;
        this.code = code;
        this.header = header;
    }

    public List<String> getHeaderValue(String name) {
        return header.get(name);
    }

    public int getReturnCode() {
        return code;
    }

    public String getGdaxValue() {
        return GdaxJson;
    }
}
