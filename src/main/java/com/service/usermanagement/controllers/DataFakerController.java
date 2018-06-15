package com.service.usermanagement.controllers;

import com.service.usermanagement.services.DataFakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/faker")
public class DataFakerController extends BaseController {
    @Autowired
    private DataFakerService dataFakerService;

    @PostMapping("/users")
    public ResponseEntity fakeUserData(@RequestParam(value = "number", defaultValue = "10") int number) {
        return dataFakerService.fakeUserData(number);
    }

    @PostMapping("/transactions")
    public ResponseEntity fakeUserTransaction(@RequestParam(value = "number", defaultValue = "100") int number) {
        return dataFakerService.fakeTransaction(number);
    }

    @PostMapping("/products")
    public ResponseEntity fakeProduct(@RequestParam(value = "number", defaultValue = "100") int number) {
        return dataFakerService.fakeProduct(number);
    }
}
