package com.bluebank.account.controller;

import com.bluebank.account.model.Account;
import com.bluebank.account.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity create(@RequestBody Account account) {
        if (accountService.customerExists(account.getCustomerId())) {
            accountService.create(account);
            return new ResponseEntity(HttpStatus.CREATED);
        } else
            return new ResponseEntity("Customer ID does not exist", HttpStatus.BAD_REQUEST);

    }

    @GetMapping(path = "{customerId}")
    public List<Account> getCustomerAccounts(@PathVariable Long customerId) {
        return accountService.getCustomerAccounts(customerId);
    }

    @GetMapping(path = "exists/{id}")
    public Boolean exists(@PathVariable Long id) {
        return accountService.exists(id);
    }

    @GetMapping(path = "credit/{id}")
    public ResponseEntity<BigDecimal> getCredit(@PathVariable Long id) {
        if (accountService.exists(id))
            return new ResponseEntity(accountService.getCredit(id), HttpStatus.OK);
        else
            return new ResponseEntity("Account does not exist", HttpStatus.BAD_REQUEST);
    }

}
