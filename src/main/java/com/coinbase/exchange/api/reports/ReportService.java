package com.coinbase.exchange.api.reports;

import com.coinbase.exchange.api.config.GdaxStaticVariables;
import com.coinbase.exchange.api.exchange.GdaxExchange;
import com.google.gson.Gson;

/**
 * Created by robevansuk on 16/02/2017.
 */
public class ReportService {

    GdaxExchange gdaxExchange;

    // TODO untested
    public ReportResponse createReport(String type, String startDate, String endDate){
        ReportRequest reportRequest = new ReportRequest(type, startDate, endDate);
        Gson gson = new Gson();
        return gdaxExchange.post(GdaxStaticVariables.REPORTS_ENDPOINT, ReportResponse.class, gson.toJson(reportRequest));
    }

    // TODO untested
    public ReportResponse getReportStatus(String id) {
        return gdaxExchange.get(GdaxStaticVariables.REPORTS_ENDPOINT + "/" + id, ReportResponse.class);
    }
}
