package com.coinbase.exchange.api.reports;

/**
 * Created by robevansuk on 16/02/2017.
 */
public class ReportResponse {

    String id;
    String type;
    String status;
    String created_at;
    String completed_at;
    String expires_at;
    String file_url;
    TimePeriod params;

    public ReportResponse() {}

    public ReportResponse(String id,
                         String type,
                         String status,
                         String created_at,
                         String completed_at,
                         String expires_at,
                         String file_url,
                         TimePeriod params) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.created_at = created_at;
        this.completed_at = completed_at;
        this.expires_at = expires_at;
        this.file_url = file_url;
        this.params = params;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getCompleted_at() {
        return completed_at;
    }

    public void setCompleted_at(String completed_at) {
        this.completed_at = completed_at;
    }

    public String getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(String expires_at) {
        this.expires_at = expires_at;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public TimePeriod getParams() {
        return params;
    }

    public void setParams(TimePeriod params) {
        this.params = params;
    }
}
