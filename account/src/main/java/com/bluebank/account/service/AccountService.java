package com.bluebank.account.service;

import com.bluebank.account.model.Account;
import com.bluebank.account.model.AccountType;
import com.bluebank.account.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final WebClient webClient;
    private final static String CHECK_IF_CUSTOMER_EXISTS_URI = "http://localhost:8081/api/v1/customer/exists/";

    public void create(Account account) {
        account.setCreatedAt(LocalDateTime.now());
        if(account.getType() == null) account.setType(AccountType.SAVINGS);
        accountRepository.save(account);
        if (account.getCredit().compareTo(BigDecimal.ZERO) > 0)
            depositCredit(account.getId(), account.getCredit());
    }

    public Boolean customerExists(Long customerId) {
        return webClient.get()
                .uri(CHECK_IF_CUSTOMER_EXISTS_URI + customerId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
    }

    public void depositCredit(Long id, BigDecimal credit) {
        // Would be better to move URI to a static final object and get it from Environment Variables
        webClient.get()
                .uri("http://localhost:8083/api/v1/transaction/deposit?accountId=" + id + "&amount=" + credit)
                .retrieve()
                .bodyToMono(ResponseEntity.class)
                .block();
    }

    public List<Account> getCustomerAccounts(Long customerId) {
        return accountRepository.findAllByCustomerId(customerId);
    }

    public Boolean exists(Long id) {
        return accountRepository.existsById(id);
    }

    public BigDecimal getCredit(Long id) {
        return accountRepository.getReferenceById(id).getCredit();
    }


}
