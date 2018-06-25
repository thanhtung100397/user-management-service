package com.service.usermanagement.services;

import com.service.usermanagement.BaseMockitoJUnitTests;
import com.service.usermanagement.constants.Constants;
import com.service.usermanagement.constants.ResponseMessage;
import com.service.usermanagement.models.dto.*;
import com.service.usermanagement.models.entities.Product;
import com.service.usermanagement.models.entities.Transaction;
import com.service.usermanagement.models.entities.User;
import com.service.usermanagement.repository.ProductRepository;
import com.service.usermanagement.repository.TransactionRepository;
import com.service.usermanagement.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.*;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

public class TransactionTests extends BaseMockitoJUnitTests {
    @InjectMocks
    TransactionService transactionService;
    @Mock
    TransactionRepository transactionRepository;
    @Mock
    ProductRepository productRepository;
    @Mock
    UserRepository userRepository;

    @Test
    public void testGetUserTransactions() {
        String userID = "ID_1";
        String wrongUserID = "WRONG_ID";

        String sortBy = Transaction.CREATED_AT;
        String sortType = Constants.DESC;
        int pageIndex = 0;
        int pageSize = Constants.MAX_PAGE_SIZE;
        int totalItem = 2;

        when(userRepository.existsById(any(String.class)))
                .thenAnswer((Answer<Boolean>) invocation -> {
                    String uid = invocation.getArgument(0);
                    return uid != null && !wrongUserID.equals(uid);
                });

        when(transactionRepository.getListUserTransactionPreviewDto(any(String.class), any(Pageable.class)))
                .thenAnswer((Answer<Page<TransactionPreviewDto>>) invocation -> {
                    Pageable pageable = invocation.getArgument(1);
                    List<TransactionPreviewDto> items = new ArrayList<>();
                    for (int i = 0; i < totalItem; i++) {
                        items.add(new TransactionPreviewDto("TID_" + (i + 1), new Date(), null));
                    }
                    return new PageImpl<>(items, pageable, items.size());
                });

        //test get user transactions with correct id
        ResponseEntity okResponse = transactionService.getUserTransactions(userID, sortBy, sortType, pageIndex, pageSize);
        Assert.assertEquals(HttpStatus.OK, okResponse.getStatusCode());
        Assert.assertNotNull(okResponse.getBody());
        Assert.assertThat(okResponse.getBody(), instanceOf(PageDto.class));
        Assert.assertEquals(totalItem, ((PageDto) okResponse.getBody()).getTotalItem());
        Assert.assertEquals(pageSize, ((PageDto) okResponse.getBody()).getPageSize());
        Assert.assertEquals(pageIndex, ((PageDto) okResponse.getBody()).getPageIndex());
        Assert.assertEquals(totalItem / pageSize + (totalItem % pageSize == 0 ? 0 : 1),
                ((PageDto) okResponse.getBody()).getTotalPage());

        //test get user with wrong id
        ResponseEntity notFoundResponse = transactionService.getUserTransactions(wrongUserID, sortBy, sortType, pageIndex, pageSize);
        Assert.assertEquals(HttpStatus.NOT_FOUND, notFoundResponse.getStatusCode());
        Assert.assertNotNull(notFoundResponse.getBody());
        Assert.assertThat(notFoundResponse.getBody(), instanceOf(MessageDto.class));
        Assert.assertEquals(ResponseMessage.USER_NOT_FOUND, ((MessageDto) notFoundResponse.getBody()).getMessage());
    }

    @Test
    public void testGetUserTransaction() {
        String userID = "ID_1";
        String wrongUserID = "WRONG_ID";
        String transactionID = "TID_1";
        String wrongTransactionID = "WRONG_TID";

        when(userRepository.existsById(any(String.class)))
                .thenAnswer((Answer<Boolean>) invocation -> {
                    String uid = invocation.getArgument(0);
                    return uid != null && !wrongUserID.equals(uid);
                });

        when(transactionRepository.getUserTransactionDto(any(String.class), any(String.class)))
                .thenAnswer((Answer<TransactionDto>) invocation -> {
                    String pid = invocation.getArgument(1);
                    if (pid == null || wrongTransactionID.equals(pid)) {
                        return null;
                    }
                    TransactionDto transactionDto = new TransactionDto();
                    transactionDto.setId(pid);
                    return transactionDto;
                });

        //test get user transaction with correct user id and correct transaction id
        ResponseEntity okResponse = transactionService.getUserTransaction(userID, transactionID);
        Assert.assertEquals(HttpStatus.OK, okResponse.getStatusCode());
        Assert.assertNotNull(okResponse.getBody());
        Assert.assertThat(okResponse.getBody(), instanceOf(TransactionDto.class));
        Assert.assertEquals(transactionID, ((TransactionDto) okResponse.getBody()).getId());

        //test get user transaction with wrong user id and correct transaction id
        ResponseEntity userNotFoundResponse = transactionService.getUserTransaction(wrongUserID, transactionID);
        Assert.assertEquals(HttpStatus.NOT_FOUND, userNotFoundResponse.getStatusCode());
        Assert.assertNotNull(userNotFoundResponse.getBody());
        Assert.assertThat(userNotFoundResponse.getBody(), instanceOf(MessageDto.class));
        Assert.assertEquals(ResponseMessage.USER_NOT_FOUND, ((MessageDto) userNotFoundResponse.getBody()).getMessage());

        //test get user transaction with correct user id and wrong transaction id
        ResponseEntity transactionNotFoundResponse = transactionService.getUserTransaction(userID, wrongTransactionID);
        Assert.assertEquals(HttpStatus.NOT_FOUND, transactionNotFoundResponse.getStatusCode());
        Assert.assertNotNull(transactionNotFoundResponse.getBody());
        Assert.assertThat(transactionNotFoundResponse.getBody(), instanceOf(MessageDto.class));
        Assert.assertEquals(ResponseMessage.USER_TRANSACTION_NOT_FOUND, ((MessageDto) transactionNotFoundResponse.getBody()).getMessage());
    }

    @Test
    public void testCreateNewUserTransaction() {
        String userID = "ID_1";
        String wrongUserID = "WRONG_ID";
        String productID = "PID_1";
        String wrongProductID = "WRONG_PID";
        NewTransactionItemsDtoSet newTransactionItemsDtoSet = new NewTransactionItemsDtoSet();
        Set<NewTransactionItemDto> transactionItemsDto = new HashSet<>();
        transactionItemsDto.add(new NewTransactionItemDto(productID, 1));
        Set<NewTransactionItemDto> wrongTransactionItemsDto = new HashSet<>();
        wrongTransactionItemsDto.add(new NewTransactionItemDto(wrongProductID, 1));

        boolean[] isNewTransactionCreated = new boolean[]{false};

        when(userRepository.findFirstById(any(String.class)))
                .thenAnswer((Answer<User>) invocation -> {
                    String uid = invocation.getArgument(0);
                    if (uid == null || wrongUserID.equals(uid)) {
                        return null;
                    }
                    return new User();
                });

        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer((Answer<Transaction>) invocation -> {
                    isNewTransactionCreated[0] = true;
                    return invocation.getArgument(0);
                });

        when(productRepository.findAllByIdIn(anySet()))
                .thenAnswer((Answer<List<Product>>) invocation -> {
                    Set<String> pids = invocation.getArgument(0);
                    List<Product> products = new ArrayList<>();
                    for (String pid : pids) {
                        if(pid == null || wrongProductID.equals(pid)) {
                            continue;
                        }
                        Product product = new Product();
                        product.setId(pid);
                        product.setPrice(new BigDecimal("1.0"));
                        products.add(product);
                    }
                    return products;
                });

        //test create new user transaction with correct user id and correct transaction product id
        newTransactionItemsDtoSet.setTransactionItems(transactionItemsDto);
        ResponseEntity okResponse = transactionService
                .createNewUserTransaction(userID, newTransactionItemsDtoSet);
        Assert.assertEquals(HttpStatus.OK, okResponse.getStatusCode());

        //test create new user transaction with wrong user id and correct transaction product id
        ResponseEntity userNotFoundRespone = transactionService
                .createNewUserTransaction(wrongUserID, newTransactionItemsDtoSet);
        Assert.assertEquals(HttpStatus.NOT_FOUND, userNotFoundRespone.getStatusCode());
        Assert.assertNotNull(userNotFoundRespone.getBody());
        Assert.assertThat(userNotFoundRespone.getBody(), instanceOf(MessageDto.class));
        Assert.assertEquals(ResponseMessage.USER_NOT_FOUND, ((MessageDto) userNotFoundRespone.getBody()).getMessage());
        Assert.assertTrue(isNewTransactionCreated[0]);

        //test create new user transaction with correct user id and wrong transaction product id
        newTransactionItemsDtoSet.setTransactionItems(wrongTransactionItemsDto);
        ResponseEntity productNotFoundResponse = transactionService.createNewUserTransaction(userID, newTransactionItemsDtoSet);
        Assert.assertEquals(HttpStatus.NOT_FOUND, productNotFoundResponse.getStatusCode());
        Assert.assertNotNull(productNotFoundResponse.getBody());
        Assert.assertThat(productNotFoundResponse.getBody(), instanceOf(ValuesErrorDto.class));
        Assert.assertEquals(((ValuesErrorDto) productNotFoundResponse.getBody()).getValues().iterator().next(), wrongProductID);
    }

    @Test
    public void testDeleteUserTransaction() {
        String userID = "ID_1";
        String wrongUserID = "WRONG_ID";
        String transactionID = "TID_1";
        String wrongTransactionID = "WRONG_TID";
        boolean[] isUserTransactionDeleted = new boolean[]{false};

        when(userRepository.existsById(any(String.class)))
                .thenAnswer((Answer<Boolean>) invocation -> {
                    String uid = invocation.getArgument(0);
                    return uid != null && !wrongUserID.equals(uid);
                });

        when(transactionRepository.existsByUser_IdAndId(any(String.class), any(String.class)))
                .thenAnswer((Answer<Boolean>) invocation -> {
                    String tid = invocation.getArgument(1);
                    return tid != null && !wrongTransactionID.equals(tid);
                });

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                isUserTransactionDeleted[0] = true;
                return null;
            }
        }).when(transactionRepository).deleteById(any(String.class));

        //test delete user with correct user id and correct transaction id
        ResponseEntity okResponse = transactionService.deleteUserTransaction(userID, transactionID);
        Assert.assertEquals(HttpStatus.OK, okResponse.getStatusCode());

        //test delete user with wrong user id and correct transaction id
        ResponseEntity userNotFoundResponse = transactionService.deleteUserTransaction(wrongUserID, transactionID);
        Assert.assertEquals(HttpStatus.NOT_FOUND, userNotFoundResponse.getStatusCode());
        Assert.assertNotNull(userNotFoundResponse.getBody());
        Assert.assertThat(userNotFoundResponse.getBody(), instanceOf(MessageDto.class));
        Assert.assertEquals(ResponseMessage.USER_NOT_FOUND, ((MessageDto) userNotFoundResponse.getBody()).getMessage());

        //test delete user with correct user id and wrong transaction id
        ResponseEntity transactionNotFoundResponse = transactionService.deleteUserTransaction(userID, wrongTransactionID);
        Assert.assertEquals(HttpStatus.NOT_FOUND, transactionNotFoundResponse.getStatusCode());
        Assert.assertNotNull(transactionNotFoundResponse.getBody());
        Assert.assertThat(transactionNotFoundResponse.getBody(), instanceOf(MessageDto.class));
        Assert.assertEquals(ResponseMessage.USER_TRANSACTION_NOT_FOUND, ((MessageDto) transactionNotFoundResponse.getBody()).getMessage());
    }
}
