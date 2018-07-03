package com.service.usermanagement.controllers;

import com.service.usermanagement.models.dto.MessageDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController extends BaseController {
    @Value("${server.port}")
    String port;

    @GetMapping("/")
    public ResponseEntity ping() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/greeting")
    public ResponseEntity sayHello() {
        return new ResponseEntity<>("Hello from user-management-service at port = "+port, HttpStatus.OK);
    }

    @GetMapping("/delayResponse")
    public ResponseEntity delayResponse(@RequestParam("millis") int millis) {
        try {
            Thread.sleep(millis);
            return new ResponseEntity(HttpStatus.OK);
        } catch (InterruptedException e) {
            return new ResponseEntity<>(new MessageDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
