package com.service.usermanagement.controllers;

import com.service.usermanagement.config.DynamicConfigurationProperties;
import com.service.usermanagement.services.ConfigurationFetchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ConfigurationController {
    @Autowired
    private ConfigurationFetchingService configurationFetchingService;

    @GetMapping("/config")
    public ResponseEntity getConfiguration() {
        try {
            return configurationFetchingService.getAllConfiguration();
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/config/{field}")
    public ResponseEntity getConfiguration(@PathVariable("field") String field) {
        return configurationFetchingService.getConfigurationField();
    }
}
