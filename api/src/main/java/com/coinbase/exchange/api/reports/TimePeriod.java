package com.coinbase.exchange.api.reports;

/**
 * Created by robevansuk on 16/02/2017.
 */
public class TimePeriod {

    String start_date;
    String end_date;

    public TimePeriod() {}

    public TimePeriod(String start_date, String end_date) {
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
}
