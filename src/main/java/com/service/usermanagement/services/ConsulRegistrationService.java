package com.service.usermanagement.services;

import com.service.usermanagement.models.dto.ConsulRegistrationPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;

@Service
public class ConsulRegistrationService {
    @Value("${consul.service-id}")
    private String serviceID;
    @Value("${spring.application.name}")
    private String serviceName;
    @Value("${consul.url}")
    private String consulServerUrl;
    @Value("${server.port}")
    private int serverPort;

    @Value("${consul.deregister-critical-service-after}")
    private String deregisterCriticalServiceAfter;
    @Value("${consul.heal-check-path}")
    private String healCheckPath;
    @Value("${consul.heal-check-interval}")
    private String healCheckInterval;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity registerServiceToConsul() {
        String healCheckUrl = "http://" + InetAddress.getLoopbackAddress().getHostAddress() + ":" + serverPort + healCheckPath;

        ConsulRegistrationPayload consulRegistrationPayload =
                new ConsulRegistrationPayload(serviceID,
                        serviceName,
                        serverPort,
                        deregisterCriticalServiceAfter,
                        healCheckUrl,
                        healCheckInterval);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<ConsulRegistrationPayload> payload = new HttpEntity<>(consulRegistrationPayload, headers);

        return restTemplate.exchange(consulServerUrl + "/v1/agent/service/register",
                HttpMethod.PUT,
                payload,
                Object.class);
    }
}
