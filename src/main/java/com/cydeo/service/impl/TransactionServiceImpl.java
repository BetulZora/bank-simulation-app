package com.cydeo.service.impl;

import com.cydeo.enums.AccountType;
import com.cydeo.exceptions.BadRequestException;
import com.cydeo.exceptions.AccountOwnershipException;
import com.cydeo.model.Account;
import com.cydeo.model.Transaction;
import com.cydeo.repository.AccountRepository;
import com.cydeo.service.TransactionService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;

    public TransactionServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Transaction makeTransfer(Account sender, Account receiver, BigDecimal amount, Date creationDate, String message) {

        // do we have enough money for transaction
        // is the target Account eligible for receiving?
        // are we sending to the same account? This is not allowed
        // if one account is saving and both must belong to the same user
            // the system allows transfer out of saving only to same user's checking

        validateAccount(sender,receiver);



        return null;
    }

    private void validateAccount(Account sender, Account receiver) {

        // is the target Account eligible for receiving and the sender too?

        if(sender==null || receiver == null){
            throw new BadRequestException("Sender or Receiver cannot be null");
        }
        // are we sending to the same account? This is not allowed
        if(sender.getId().equals( receiver.getId())){
            throw new BadRequestException("Sender and Receiver accounts need to be different");
        }
        findAccountById(sender.getId());
        findAccountById(receiver.getId());

        checkAccountOwnership(sender,receiver);




    }

    private void checkAccountOwnership(Account sender, Account receiver) {

        // write an if statement that checks if one of the account is a saving account
        // user of sender or receiver is not the same throw AccountOwnershipException

        if( sender.getAccountType() == AccountType.SAVING || receiver.getAccountType()==AccountType.SAVING && (!sender.getUserID().equals(receiver.getUserID()))){

            throw new AccountOwnershipException("Cannot transfer from someone else's Saving account");

        }else{
            // complete transaction
        }





    }

    private void findAccountById(UUID id) {

        accountRepository.findById(id);


    }

    @Override
    public List<Transaction> findAllTransaction() {
        return null;
    }
}
