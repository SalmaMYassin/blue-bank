package com.bluebank.customer.controller;

import com.bluebank.customer.model.Customer;
import com.bluebank.customer.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.Pipe;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/customer")
public record CustomerController(CustomerService customerService) {

    @PostMapping
    public void register(@RequestBody Customer customer){
        log.info("New Customer Registration {}", customer);
        customerService.registerCustomer(customer);
    }
}
