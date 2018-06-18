package com.service.usermanagement.controllers;

import com.service.usermanagement.models.dto.MessageDto;
import com.service.usermanagement.models.dto.PageDto;
import com.service.usermanagement.models.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HealthController extends BaseController {
    @Value("${server.port}")
    String port;

    @RequestMapping("/")
    public ResponseEntity ping() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping("/health")
    public ResponseEntity healthCheck() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping("/greeting")
    public ResponseEntity sayHello() {
        return new ResponseEntity<>("Hello from user-management-service at port = "+port, HttpStatus.OK);
    }

    @RequestMapping("/delayResponse")
    public ResponseEntity delayResponse(@RequestParam("millis") int millis) {
        try {
            Thread.sleep(millis);
            return new ResponseEntity(HttpStatus.OK);
        } catch (InterruptedException e) {
            return new ResponseEntity<>(new MessageDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/testDiscovery")
    public ResponseEntity testDiscovery() {
        return restTemplate.exchange("http://user-management-service/users", HttpMethod.GET,
                null, new ParameterizedTypeReference<PageDto<UserDto>>() {});
    }
}
