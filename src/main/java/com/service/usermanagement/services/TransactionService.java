package com.service.usermanagement.services;

import com.service.usermanagement.constants.ResponseMessage;
import com.service.usermanagement.repository.ProductRepository;
import com.service.usermanagement.repository.TransactionRepository;
import com.service.usermanagement.repository.UserRepository;
import com.service.usermanagement.models.dto.*;
import com.service.usermanagement.models.entities.Product;
import com.service.usermanagement.models.entities.Transaction;
import com.service.usermanagement.models.entities.TransactionItem;
import com.service.usermanagement.models.entities.User;
import com.service.usermanagement.utils.PageRequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity getUserTransactions(String userID,
                                              String sortBy, String sortType,
                                              Integer pageIndex, Integer pageSize) {
        boolean isUserExist = userRepository.existsById(userID);
        if (!isUserExist) {
            return new ResponseEntity<>(new MessageDto(ResponseMessage.USER_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        Pageable pageable = new PageRequestBuilder()
                .sort(sortBy, sortType)
                .page(pageIndex, pageSize)
                .build();
        Page<TransactionPreviewDto> queryResult = transactionRepository.getListUserTransactionPreviewDto(userID, pageable);
        PageDto<TransactionPreviewDto> responseBody = new PageDto<>(queryResult);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    public ResponseEntity getUserTransaction(String userID, String transactionID) {
        boolean isUserExist = userRepository.existsById(userID);
        if (!isUserExist) {
            return new ResponseEntity<>(new MessageDto(ResponseMessage.USER_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        TransactionDto queryResult = transactionRepository.getUserTransactionDto(userID, transactionID);
        if (queryResult == null) {
            return new ResponseEntity<>(new MessageDto(ResponseMessage.USER_TRANSACTION_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(queryResult, HttpStatus.OK);
    }

    public ResponseEntity createNewUserTransaction(String userID, NewTransactionItemsDtoSet newTransactionItemsDtoSet) {
        User userFound = userRepository.findFirstById(userID);
        if (userFound == null) {
            return new ResponseEntity<>(new MessageDto(ResponseMessage.USER_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        Set<String> productIDs = new HashSet<>();
        Map<String, Integer> productQuantities = new HashMap<>();
        for (NewTransactionItemDto transactionItemDto : newTransactionItemsDtoSet.getTransactionItems()) {
            productIDs.add(transactionItemDto.getProductID());
            productQuantities.put(transactionItemDto.getProductID(), transactionItemDto.getQuantity());
        }
        List<Product> productFounds = productRepository.findAllByIdIn(productIDs);
        Transaction transaction = new Transaction();
        List<TransactionItem> transactionItems = new ArrayList<>(productFounds.size());
        for (Product product : productFounds) {
            TransactionItem transactionItem = new TransactionItem();
            transactionItem.setProduct(product);
            transactionItem.setTransaction(transaction);
            int quantity = productQuantities.get(product.getId());
            BigDecimal price = new BigDecimal(quantity).multiply(product.getPrice());
            transactionItem.setQuantity(quantity);
            transactionItem.setPrice(price);
            transactionItems.add(transactionItem);
            productQuantities.remove(product.getId());
        }
        if (productQuantities.isEmpty()) {
            transaction.setUser(userFound);
            transaction.setTransactionItems(transactionItems);
            transactionRepository.save(transaction);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            Set<String> notFoundProductIDs = new HashSet<>();
            for (Map.Entry<String, Integer> entry : productQuantities.entrySet()) {
                notFoundProductIDs.add(entry.getKey());
            }
            return new ResponseEntity<>(new ValuesErrorDto(ResponseMessage.PRODUCT_NOT_FOUND, notFoundProductIDs),
                    HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity deleteUserTransaction(String userID, String transactionID) {
        if (!userRepository.existsById(userID)) {
            return new ResponseEntity<>(new MessageDto(ResponseMessage.USER_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        if (!transactionRepository.existsByUser_IdAndId(userID, transactionID)) {
            return new ResponseEntity<>(new MessageDto(ResponseMessage.USER_TRANSACTION_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        transactionRepository.deleteById(transactionID);
        return new ResponseEntity(HttpStatus.OK);
    }
}
