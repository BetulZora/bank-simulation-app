package com.cydeo.service;

import com.cydeo.enums.AccountType;
import com.cydeo.model.Account;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface AccountService {

    // Create an account
    Account createNewAccount(BigDecimal balance, Date createDate, AccountType accountType, Long userId);

    //List of all accounts
    List<Account> listAllAccounts();



}
