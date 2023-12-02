package com.cydeo.service;

import com.cydeo.dto.AccountDTO;
import com.cydeo.enums.AccountType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface AccountService {

    // Create an account
    AccountDTO createNewAccount(BigDecimal balance, Date createDate, AccountType accountType, Long userId);

    //List of all accounts
    List<AccountDTO> listAllAccounts();

    // Delete an account by ID
    void deleteByID(UUID id);

    void activateByID(UUID id);

    AccountDTO retrieveByID(UUID id);





}
