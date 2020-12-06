package com.coinbase.exchange.api.reports;

import com.coinbase.exchange.api.exchange.CoinbaseExchange;
import lombok.Data;
import org.springframework.core.ParameterizedTypeReference;

@Data
public class ReportService {

	private static final String REPORTS_ENDPOINT = "/reports";

	private final CoinbaseExchange coinbaseExchange;

	// TODO untested
	public ReportResponse createReport(String type, String startDate, String endDate) {
		ReportRequest reportRequest = new ReportRequest(type, startDate, endDate);
		return coinbaseExchange.post(REPORTS_ENDPOINT, new ParameterizedTypeReference<>() {
		}, reportRequest);
	}

	// TODO untested
	public ReportResponse getReportStatus(String id) {
		return coinbaseExchange.get(REPORTS_ENDPOINT + "/" + id, new ParameterizedTypeReference<>() {
		});
	}
}
