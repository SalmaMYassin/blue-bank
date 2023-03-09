package com.bluebank.customer;

import com.bluebank.customer.model.Customer;
import com.bluebank.customer.service.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class CustomerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }

    @Bean
    CommandLineRunner run(CustomerService customerService) {
        return args -> {
            Customer customer = new Customer(1L, "Salma","Yassin","syassin@gmail.com");
            Customer customer1 = new Customer(2L, "Mohamed","Wael","mwael@gmail.com");
            Customer customer2 = new Customer(3L, "Sohila","Abdou","sabdou@gmail.com");
           customerService.registerCustomer(customer);
           customerService.registerCustomer(customer1);
           customerService.registerCustomer(customer2);
        };
    }
}
