package com.coinbase.exchange.model;

public enum Granularity {
    ONE_DAY("1d"),
    SIX_HOURS("6h"),
    ONE_HOUR("1h"),
    FIFTEEN_MIN("15m"),
    FIVE_MIN("5m"),
    ONE_MIN("1m");

    private String granularity;

    Granularity(String granularity) {
        this.granularity = granularity;
    }

    /**
     * The granularity field must be one of the following values:
     * {60, 300, 900, 3600, 21600, 86400}.
     */
    public String get(){
        switch(granularity) {
            case "1d": return "86400";
            case "6h": return "21600";
            case "1h": return "3600";
            case "15m": return "900";
            case "5m": return "300";
            case "1m": return "60";
        }
        return "";
    }
}
