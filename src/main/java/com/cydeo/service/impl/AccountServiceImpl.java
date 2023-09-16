package com.cydeo.service.impl;

import com.cydeo.enums.AccountType;
import com.cydeo.model.Account;
import com.cydeo.repository.AccountRepository;
import com.cydeo.service.AccountService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class AccountServiceImpl implements AccountService {

    AccountRepository accountRepository;
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    public Account createNewAccount(BigDecimal balance, Date createDate, AccountType accountType, Long userId) {
        // need to create the account,
        Account account = Account.builder().id(UUID.randomUUID()).userID(userId)
                .balance(balance).accountType(accountType).creationDate(createDate).build();
        // save it in the database (repository) and
        // then return it

        return accountRepository.save(account);
    }

    @Override
    public List<Account> listAllAccounts() {
        return accountRepository.findall();
    }
}
