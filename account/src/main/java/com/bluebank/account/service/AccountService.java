package com.bluebank.account.service;

import com.bluebank.account.model.Account;
import com.bluebank.account.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final WebClient webClient;
    private final static String CHECK_IF_CUSTOMER_EXISTS_URI = "http://localhost:8081/api/v1/customer/exists/";


    public void create(Account account) {
        // TODO: if credit is not = 0 then send transaction
        accountRepository.save(account);
    }

    public Boolean customerExists(Long customerId){
        return  webClient.get()
                .uri(CHECK_IF_CUSTOMER_EXISTS_URI + customerId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
    }
    public List<Account> getCustomerAccounts(Long customerId){

        return accountRepository.findAllByCustomerId(customerId);
    }

}
