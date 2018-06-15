package com.service.usermanagement.controllers;

import com.service.usermanagement.constants.Constants;
import com.service.usermanagement.models.dto.NewTransactionItemDto;
import com.service.usermanagement.models.dto.PageDto;
import com.service.usermanagement.models.dto.TransactionDto;
import com.service.usermanagement.models.dto.TransactionPreviewDto;
import com.service.usermanagement.models.entities.Transaction;
import com.service.usermanagement.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/users/{uid}/transactions")
    public ResponseEntity createNewUserTransaction(@PathVariable("uid") String userID,
                                                   @RequestBody @Valid Set<NewTransactionItemDto> newTransactionItemsDto) {
        return transactionService.createNewUserTransaction(userID, newTransactionItemsDto);
    }

    @GetMapping("/users/{uid}/transactions")
    public ResponseEntity<PageDto<TransactionPreviewDto>> getUserTransactions(@PathVariable("uid") String userID,
                                                                              @RequestParam(required = false, defaultValue = Transaction.CREATED_AT) String sortBy,
                                                                              @RequestParam(required = false, defaultValue = Constants.DESC) String sortType,
                                                                              @RequestParam(required = false, defaultValue = "0") Integer pageIndex,
                                                                              @RequestParam(required = false, defaultValue = Constants.MAX_PAGE_SIZE + "") Integer pageSize) {
        return transactionService.getUserTransactions(userID, sortBy, sortType, pageIndex, pageSize);
    }

    @GetMapping("/users/{uid}/transactions/{tid}")
    public ResponseEntity<TransactionDto> getUserTransaction(@PathVariable("uid") String userID,
                                                             @PathVariable("tid") String transactionID) {
        return transactionService.getUserTransaction(userID, transactionID);
    }

    @DeleteMapping("/users/{uid}/transactions/{tid}")
    public ResponseEntity deleteUserTransaction(@PathVariable("uid") String userID,
                                                @PathVariable("tid") String transactionID) {
        return transactionService.deleteUserTransaction(userID, transactionID);
    }
}
