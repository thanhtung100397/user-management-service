package com.service.usermanagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ConsulDeregistrationService {
    @Value("${consul.service-id}")
    private String serviceID;
    @Value("${consul.url}")
    private String consulServerUrl;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity deregisterServiceAtConsul() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity payload = new HttpEntity<>(headers);

        return restTemplate.exchange(consulServerUrl + "/v1/agent/service/deregister/" + serviceID,
                HttpMethod.PUT,
                payload,
                Object.class);
    }
}
