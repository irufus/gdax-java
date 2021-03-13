package com.coinbase.exchange.api.reports;

import com.coinbase.exchange.api.exchange.CoinbaseExchange;
import com.coinbase.exchange.api.exchange.CoinbaseExchangeException;
import org.springframework.core.ParameterizedTypeReference;

/**
 * Created by robevansuk on 16/02/2017.
 */
public class ReportService {

    private static final String REPORTS_ENDPOINT = "/reports";

    final CoinbaseExchange coinbaseExchange;

    public ReportService(final CoinbaseExchange coinbaseExchange) {
        this.coinbaseExchange = coinbaseExchange;
    }

    // TODO untested
    public ReportResponse createReport(String type, String startDate, String endDate) throws CoinbaseExchangeException {
        ReportRequest reportRequest = new ReportRequest(type, startDate, endDate);
        return coinbaseExchange.post(REPORTS_ENDPOINT, new ParameterizedTypeReference<ReportResponse>(){}, reportRequest);
    }

    // TODO untested
    public ReportResponse getReportStatus(String id) throws CoinbaseExchangeException {
        return coinbaseExchange.get(REPORTS_ENDPOINT + "/" + id, new ParameterizedTypeReference<ReportResponse>(){});
    }
}
