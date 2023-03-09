package com.bluebank.transaction.service;


import com.bluebank.transaction.model.Transaction;
import com.bluebank.transaction.model.TransactionType;
import com.bluebank.transaction.repository.TransactionRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final WebClient webClient;
    private final static String GET_ACCOUNT_CREDIT = "http://localhost:8082/api/v1/account/credit/";
    private final static String CHECK_IF_ACCOUNT_EXISTS = "http://localhost:8082/api/v1/account/exists/";


    public void deposit(Long accountId, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setAccountId(accountId);
        transaction.setAmount(amount);

        log.info("deposit transaction is being saved");
        transactionRepository.save(transaction);
    }

    public void withdraw(Long accountId, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.WITHDRAW);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setAccountId(accountId);
        transaction.setAmount(amount);

        log.info("withdraw transaction is being saved");

        transactionRepository.save(transaction);
    }

    public Optional<Transaction> get(Long id) {
        return transactionRepository.findById(id);
    }

    public Page<Transaction> getAllByAccountId(Long accountId, int page, int size) {
        return transactionRepository.findAllByAccountId(accountId, PageRequest.of(page, size));
    }

    public Boolean hasEnoughCredit(Long accountId, BigDecimal amount) {
        return amount.compareTo(webClient.get()
                .uri(GET_ACCOUNT_CREDIT + accountId)
                .retrieve()
                .bodyToMono(BigDecimal.class)
                .block()) > 0;
    }
    public Boolean accountExists(Long accountId) {
        return webClient.get()
                .uri(CHECK_IF_ACCOUNT_EXISTS + accountId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
    }


}
