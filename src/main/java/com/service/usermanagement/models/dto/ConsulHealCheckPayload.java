package com.service.usermanagement.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConsulHealCheckPayload {
    @JsonProperty("DeregisterCriticalServiceAfter")
    private String DeregisterCriticalServiceAfter;
    @JsonProperty("HTTP")
    private String HTTP;
    @JsonProperty("Interval")
    private String Interval;

    public ConsulHealCheckPayload(String deregisterCriticalServiceAfter,
                                  String http,
                                  String interval) {
        this.DeregisterCriticalServiceAfter = deregisterCriticalServiceAfter;
        this.HTTP = http;
        this.Interval = interval;
    }

    public String getDeregisterCriticalServiceAfter() {
        return DeregisterCriticalServiceAfter;
    }

    public void setDeregisterCriticalServiceAfter(String deregisterCriticalServiceAfter) {
        DeregisterCriticalServiceAfter = deregisterCriticalServiceAfter;
    }

    public String getHTTP() {
        return HTTP;
    }

    public void setHTTP(String HTTP) {
        this.HTTP = HTTP;
    }

    public String getInterval() {
        return Interval;
    }

    public void setInterval(String interval) {
        Interval = interval;
    }
}
