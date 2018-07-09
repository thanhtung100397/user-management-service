package com.service.usermanagement.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties
public class DynamicConfigurationProperties {
    private String test;

    @RefreshScope
    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}
