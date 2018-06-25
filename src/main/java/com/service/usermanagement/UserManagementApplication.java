package com.service.usermanagement;

import com.service.usermanagement.services.DataFakerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@SpringBootApplication
//@EnableEurekaClient	`
public class UserManagementApplication implements CommandLineRunner {
//	@Bean
//	@LoadBalanced
//	public RestTemplate restTemplate(){
//		return new RestTemplate();
//	}

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
