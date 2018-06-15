package com.service.usermanagement.services;

import com.github.javafaker.Faker;
import com.service.usermanagement.constants.Constants;
import com.service.usermanagement.constants.ResponseMessage;
import com.service.usermanagement.dao.ProductRepository;
import com.service.usermanagement.dao.TransactionRepository;
import com.service.usermanagement.dao.UserRepository;
import com.service.usermanagement.models.dto.MessageDto;
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

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class DataFakerService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    public ResponseEntity fakeUserData(int number) {
        Faker faker = new Faker(Locale.US);

        List<User> fakeUsers = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            User fakeUser = new User();
            fakeUser.setFullName(faker.name().fullName());
            fakeUser.setBirthday(faker.date().birthday());
            fakeUser.setAddress(faker.address().fullAddress());
            fakeUser.setGender(faker.random().nextBoolean() ? Constants.FEMALE : Constants.MALE);
            fakeUsers.add(fakeUser);
        }
        userRepository.saveAll(fakeUsers);
        return new ResponseEntity<>(new MessageDto(fakeUsers.size() + " " + ResponseMessage.FAKE_USER_CREATED), HttpStatus.OK);
    }

    public ResponseEntity fakeProduct(int number) {
        Faker faker = new Faker();

        List<Product> fakeProducts = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            Product fakeProduct = new Product();
            fakeProduct.setName(faker.commerce().productName());
            fakeProduct.setPrice(Double.parseDouble(faker.commerce().price().replace(",", ".")));
            fakeProducts.add(fakeProduct);
        }
        productRepository.saveAll(fakeProducts);
        return new ResponseEntity<>(new MessageDto(fakeProducts.size() + " " + ResponseMessage.FAKE_PRODUCT_CREATED), HttpStatus.OK);
    }

    public ResponseEntity fakeTransaction(int number) {
        Faker faker = new Faker();

        List<Transaction> fakeTransactions = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            Transaction fakeTransaction = new Transaction();
            User randomUser = getRandomUser(faker);
            if (randomUser == null) {
                break;
            }
            fakeTransaction.setUser(randomUser);
            Date fakeCreatedDate = faker.date().past(90, TimeUnit.DAYS);
            Date fakeModifiedDate = faker.random().nextBoolean() ?
                    null : faker.date().future(90, TimeUnit.DAYS, fakeCreatedDate);
            fakeTransaction.setCreatedAt(fakeCreatedDate);
            fakeTransaction.setModifiedAt(fakeModifiedDate);
            fakeTransaction.setTransactionItems(getRandomTransactionItems(faker, fakeTransaction, faker.random().nextInt(5) + 1));
            fakeTransactions.add(fakeTransaction);
        }
        transactionRepository.saveAll(fakeTransactions);
        return new ResponseEntity<>(new MessageDto(fakeTransactions.size() + " " + ResponseMessage.FAKE_USER_TRANSACTION_CREATED), HttpStatus.OK);
    }

    private User getRandomUser(Faker faker) {
        long userCount = userRepository.count();
        if (userCount == 0) {
            return null;
        }
        int randomUserIndex = faker.random().nextInt((int) userCount);
        Pageable pageable = new PageRequestBuilder()
                .page(randomUserIndex, 1)
                .build();
        Page<User> randomUsersPage = userRepository.findAll(pageable);
        return randomUsersPage.getContent().get(0);
    }

    private Product getRandomProduct(Faker faker) {
        long productCount = productRepository.count();
        if(productCount == 0) {
            return null;
        }
        int randomProductIndex = faker.random().nextInt((int) productCount);
        Pageable pageable = new PageRequestBuilder()
                .page(randomProductIndex, 1)
                .build();
        Page<Product> randomProductsPage = productRepository.findAll(pageable);
        return randomProductsPage.getContent().get(0);
    }

    private List<TransactionItem> getRandomTransactionItems(Faker faker, Transaction transaction, int number) {
        if(number <= 0) {
            number = 1;
        }
        List<TransactionItem> fakeTransactionItems = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            TransactionItem fakeTransactionItem = new TransactionItem();
            fakeTransactionItem.setTransaction(transaction);
            Product randomProduct = getRandomProduct(faker);
            if(randomProduct == null) {
                break;
            }
            fakeTransactionItem.setProduct(randomProduct);
            int fakeQuantity = faker.number().randomDigitNotZero();
            fakeTransactionItem.setQuantity(faker.number().randomDigitNotZero());
            fakeTransactionItem.setPrice(fakeQuantity * randomProduct.getPrice());
            fakeTransactionItems.add(fakeTransactionItem);
        }
        return fakeTransactionItems;
    }
}
