package com.cydeo.service.impl;

import com.cydeo.dto.AccountDTO;
import com.cydeo.entity.Account;
import com.cydeo.enums.AccountStatus;
import com.cydeo.enums.AccountType;
import com.cydeo.mapper.AccountMapper;
import com.cydeo.repository.AccountRepository;
import com.cydeo.service.AccountService;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    AccountRepository accountRepository;
    AccountMapper accountMapper;

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public void createNewAccount(AccountDTO newAccount) {
        newAccount.setCreationDate(new Date());
        newAccount.setAccountStatus(AccountStatus.ACTIVE);
        accountRepository.save(accountMapper.convertToEntity(newAccount));
    }

    @Override
    public List<AccountDTO> listAllAccounts() {
        return accountRepository.findAll().stream().map(accountMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        // find the account
        Account accountDTOToDelete = accountRepository.findById(id).get();
        // change the status to deleted
        accountDTOToDelete.setAccountStatus(AccountStatus.DELETED);
        // save it to update the DB
        accountRepository.save(accountDTOToDelete);
    }

    @Override
    public void activateAccount(Long id) {
        // Find the entity to update
        Account account = accountRepository.findById(id).get();
        // Update the status
        account.setAccountStatus(AccountStatus.ACTIVE);
        // save to update DB
        accountRepository.save(account);
    }

    @Override
    public AccountDTO retrieveByID(Long id) {
        return accountMapper.convertToDTO(accountRepository.findById(id).get());
    }
}
