package com.service.usermanagement.controllers;

import com.service.usermanagement.models.dto.MessageDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController extends BaseController {
    @Value("${server.port}")
    String port;

    @RequestMapping("/")
    public ResponseEntity ping() {
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
}
