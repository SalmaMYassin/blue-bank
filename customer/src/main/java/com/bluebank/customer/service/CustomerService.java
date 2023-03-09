package com.bluebank.customer.service;

import com.bluebank.customer.model.Customer;
import com.bluebank.customer.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    
    public void registerCustomer(Customer customer){
        // TODO: check if email is valid, if its not taken
        customerRepository.save(customer);
    }

    public Boolean exists(Long id){
        return customerRepository.existsById(id);
    }

    public Customer getCustomerData(Long id){
        return customerRepository.findById(id).orElse(null);
    }
}
