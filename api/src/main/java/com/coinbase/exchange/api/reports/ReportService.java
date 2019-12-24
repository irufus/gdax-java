package com.coinbase.exchange.api.reports;

import com.coinbase.exchange.api.exchange.CoinbasePro;
import org.springframework.core.ParameterizedTypeReference;

/**
 * Created by robevansuk on 16/02/2017.
 */
public class ReportService {

    private static final String REPORTS_ENDPOINT = "/reports";

    final CoinbasePro coinbasePro;

    public ReportService(final CoinbasePro coinbasePro) {
        this.coinbasePro = coinbasePro;
    }

    // TODO untested
    public ReportResponse createReport(String type, String startDate, String endDate){
        ReportRequest reportRequest = new ReportRequest(type, startDate, endDate);
        return coinbasePro.post(REPORTS_ENDPOINT, new ParameterizedTypeReference<ReportResponse>(){}, reportRequest);
    }

    // TODO untested
    public ReportResponse getReportStatus(String id) {
        return coinbasePro.get(REPORTS_ENDPOINT + "/" + id, new ParameterizedTypeReference<ReportResponse>(){});
    }
}
