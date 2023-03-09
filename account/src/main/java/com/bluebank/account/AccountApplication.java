package com.bluebank.account;

import com.bluebank.account.model.Account;
import com.bluebank.account.model.AccountType;
import com.bluebank.account.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootApplication
public class AccountApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class,args);
    }

    @Bean
    CommandLineRunner run(AccountService accountService) {
        return args -> {
            Account account = new Account(null, AccountType.SAVINGS, LocalDateTime.now(),new BigDecimal(200), 1L);
            Account account1 = new Account(null, AccountType.CHECKING, LocalDateTime.now(),new BigDecimal(8000), 1L);
            Account account2 = new Account(null, AccountType.SAVINGS, LocalDateTime.now(),new BigDecimal(0), 2L);
            accountService.create(account);
            accountService.create(account1);
            accountService.create(account2);
        };
    }
}
