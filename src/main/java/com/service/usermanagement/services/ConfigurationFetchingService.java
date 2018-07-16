package com.service.usermanagement.services;

import com.service.usermanagement.config.DynamicConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class ConfigurationFetchingService {
    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private DynamicConfigurationProperties dynamicConfigurationProperties;

    public ResponseEntity getAllConfiguration() throws IOException {
        StringBuilder content = new StringBuilder();
        content.append(readConfigFile("application.properties"));
        content.append("</br>");
        content.append(readConfigFile("bootstrap.yml"));
        return new ResponseEntity<>(content.toString(), HttpStatus.OK);
    }

    private String readConfigFile(String fileName) throws IOException {
        StringBuilder content = new StringBuilder();
        InputStream inputStream = resourceLoader
                .getResource("classpath:" + fileName)
                .getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        content.append("<h1>").append(fileName).append("</h1>");
        while ((line = bufferedReader.readLine()) != null) {
            if (line.isEmpty()) {
                content.append("</br>");
            } else {
                content.append("<p>").append(line.replace(" ", "&nbsp")).append("</p>");
            }
        }
        return content.toString();
    }

//    public ResponseEntity getConfigurationField() {
//        return new ResponseEntity<>(dynamicConfigurationProperties.getTest(), HttpStatus.OK);
//    }
}
