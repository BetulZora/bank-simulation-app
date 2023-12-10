package com.cydeo.service;

import com.cydeo.dto.AccountDTO;
import com.cydeo.entity.Account;

import java.util.List;

public interface AccountService {

    // Create an account
    void createNewAccount(AccountDTO newAccount);

    //List of all accounts
    List<AccountDTO> listAllAccounts();

    // Delete an account by ID
    void deleteAccount(Long id);

    void activateAccount(Long id);

    AccountDTO retrieveByID(Long id);

    List<AccountDTO> listAllActiveAccounts();

    void updateAccount(AccountDTO dto);
}
