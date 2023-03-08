package com.bluebank.customer.service;

import com.bluebank.customer.model.Customer;
import com.bluebank.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public record CustomerService(CustomerRepository customerRepository) {
    public void registerCustomer(Customer customer){
        // TODO: check if email is valid, if its not taken
        customerRepository.save(customer);
    }
}
