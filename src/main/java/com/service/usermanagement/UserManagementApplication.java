package com.service.usermanagement;

import com.service.usermanagement.services.DataFakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
//@EnableDiscoveryClient
public class UserManagementApplication implements CommandLineRunner {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(UserManagementApplication.class, args);
    }

    @Autowired
    private DataFakerService dataFakerService;
    @Value("${fakedata}")
    private String fakeData;

    @Override
    public void run(String... args) throws Exception {
        if (fakeData.equals("true")) {
            dataFakerService.fakeUserData(10);
            System.out.println("Fake 10 user data");
            dataFakerService.fakeProduct(10);
            System.out.println("Fake 10 product");
            dataFakerService.fakeTransaction(10);
            System.out.println("Fake 10 transaction");
        }
    }
}
