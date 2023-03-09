package com.bluebank.transaction.controller;

import com.bluebank.transaction.model.Transaction;
import com.bluebank.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
@Slf4j
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("/deposit")
    public ResponseEntity deposit(@RequestParam(name = "accountId") Long accountId,
                                  @RequestParam(name = "amount") BigDecimal amount) {
        if (transactionService.accountExists(accountId)) {
            transactionService.deposit(accountId, amount);
            log.info("Amount deposited to account");
            return new ResponseEntity(HttpStatus.OK);
        } else
            return new ResponseEntity("Account does not exist", HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/withdraw")
    public ResponseEntity withdraw(@RequestParam(name = "accountId") Long accountId,
                                   @RequestParam(name = "amount") BigDecimal amount) {

        if (transactionService.hasEnoughCredit(accountId, amount)) {
            transactionService.withdraw(accountId, amount);
            log.info("Amount withdrawn from account");
            return new ResponseEntity(HttpStatus.OK);
        } else
            return new ResponseEntity("Not enough credit", HttpStatus.BAD_REQUEST);

    }

    @GetMapping(path = "{id}")
    public Optional<Transaction> get(@PathVariable Long id) {
        return transactionService.get(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Transaction> getAll(@RequestParam(name = "accountId") Long accountId,
                                    @RequestParam(name = "page", defaultValue = "0") int page,
                                    @RequestParam(name = "size", defaultValue = "4") int size) {
        return transactionService.getAllByAccountId(accountId, page, size);
    }
}
