package com.bluebank.customer.controller;

import com.bluebank.customer.model.Customer;
import com.bluebank.customer.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.Pipe;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/customer")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public void register(@RequestBody Customer customer){
        log.info("New Customer Registration {}", customer);
        customerService.registerCustomer(customer);
    }

    @GetMapping("exists/{id}")
    public Boolean exists(@PathVariable Long id){
        return customerService.exists(id);
    }
}
