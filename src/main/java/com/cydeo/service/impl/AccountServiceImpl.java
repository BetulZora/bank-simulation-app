package com.cydeo.service.impl;

import com.cydeo.enums.AccountStatus;
import com.cydeo.enums.AccountType;
import com.cydeo.model.Account;
import com.cydeo.repository.AccountRepository;
import com.cydeo.service.AccountService;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    AccountRepository accountRepository;
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    public Account createNewAccount(BigDecimal balance, Date createDate, AccountType accountType, Long userId) {
        // need to create the account,
        // Assign default AccountStatus as Active when account is created
        Account account = Account.builder().id(UUID.randomUUID()).userID(userId)
                .balance(balance).accountType(accountType).creationDate(createDate).accountStatus(AccountStatus.ACTIVE).build();
        // save it in the database (repository) and
        // then return it

        return accountRepository.save(account);
    }

    @Override
    public List<Account> listAllAccounts() {
        return accountRepository.findall();
    }

    @Override
    public void deleteByID(UUID id) {
        // find the account
        Account accountToDelete = accountRepository.findById(id);
        // change the status to deleted
        accountToDelete.setAccountStatus(AccountStatus.DELETED);
    }

    @Override
    public void activateByID(UUID id) {
        accountRepository.findById(id).setAccountStatus(AccountStatus.ACTIVE);
    }

    @Override
    public Account retrieveByID(UUID id) {
        return accountRepository.findById(id);
    }
}
