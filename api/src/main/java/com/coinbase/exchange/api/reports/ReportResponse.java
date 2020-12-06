package com.coinbase.exchange.api.reports;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponse {
	@JsonProperty("id")
	private String id;
	@JsonProperty("type")
	private String type;
	@JsonProperty("status")
	private String status;
	@JsonProperty("created_at")
	private String createdAt;
	@JsonProperty("completed_at")
	private String completedAt;
	@JsonProperty("expires_at")
	private String expiresAt;
	@JsonProperty("file_url")
	private String fileUrl;
	@JsonProperty("params")
	private TimePeriod params;
}
