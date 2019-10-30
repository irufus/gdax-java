package com.coinbase.exchange.api.reports;

import com.coinbase.exchange.api.exchange.GdaxExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

/**
 * Created by robevansuk on 16/02/2017.
 */
@Component
public class ReportService {

    private static final String REPORTS_ENDPOINT = "/reports";

    @Autowired
    GdaxExchange gdaxExchange;

    // TODO untested
    public ReportResponse createReport(String type, String startDate, String endDate){
        ReportRequest reportRequest = new ReportRequest(type, startDate, endDate);
        return gdaxExchange.post(REPORTS_ENDPOINT, new ParameterizedTypeReference<ReportResponse>(){}, reportRequest);
    }

    // TODO untested
    public ReportResponse getReportStatus(String id) {
        return gdaxExchange.get(REPORTS_ENDPOINT + "/" + id, new ParameterizedTypeReference<ReportResponse>(){});
    }
}
