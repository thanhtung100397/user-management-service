package com.service.usermanagement.controllers;

import com.service.usermanagement.constants.Constants;
import com.service.usermanagement.models.dto.*;
import com.service.usermanagement.models.entities.Transaction;
import com.service.usermanagement.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class TransactionController extends BaseController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/users/{uid}/transactions")
    public ResponseEntity createNewUserTransaction(@PathVariable("uid") String userID,
                                                   @RequestBody @Valid NewTransactionItemsDtoSet newTransactionItemsDtoSet) {
        return transactionService.createNewUserTransaction(userID, newTransactionItemsDtoSet);
    }

    @GetMapping("/users/{uid}/transactions")
    public ResponseEntity getUserTransactions(@PathVariable("uid") String userID,
                                              @RequestParam(required = false, defaultValue = Transaction.CREATED_AT) String sortBy,
                                              @RequestParam(required = false, defaultValue = Constants.DESC) String sortType,
                                              @RequestParam(required = false, defaultValue = "0") Integer pageIndex,
                                              @RequestParam(required = false, defaultValue = Constants.MAX_PAGE_SIZE + "") Integer pageSize) {
        return transactionService.getUserTransactions(userID, sortBy, sortType, pageIndex, pageSize);
    }

    @GetMapping("/users/{uid}/transactions/{tid}")
    public ResponseEntity getUserTransaction(@PathVariable("uid") String userID,
                                             @PathVariable("tid") String transactionID) {
        return transactionService.getUserTransaction(userID, transactionID);
    }

    @DeleteMapping("/users/{uid}/transactions/{tid}")
    public ResponseEntity deleteUserTransaction(@PathVariable("uid") String userID,
                                                @PathVariable("tid") String transactionID) {
        return transactionService.deleteUserTransaction(userID, transactionID);
    }
}
