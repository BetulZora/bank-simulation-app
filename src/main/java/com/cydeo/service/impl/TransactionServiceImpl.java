package com.cydeo.service.impl;

import com.cydeo.enums.AccountType;
import com.cydeo.exceptions.BadRequestException;
import com.cydeo.exceptions.AccountOwnershipException;
import com.cydeo.exceptions.BalanceNotSufficientException;
import com.cydeo.exceptions.UnderConstructionException;
import com.cydeo.model.Account;
import com.cydeo.model.Transaction;
import com.cydeo.repository.AccountRepository;
import com.cydeo.repository.TransactionRepository;
import com.cydeo.service.TransactionService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class TransactionServiceImpl implements TransactionService {

    @Value("${under_construction}")
    private boolean underConstruction;
    // Use the @Value annotation to get values from the properties file


    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @SneakyThrows
    @Override
    public Transaction makeTransfer(Account sender, Account receiver, BigDecimal amount, Date creationDate, String message) {

        //make transaction if app is not under construction
        if(!underConstruction) {

            // do we have enough money for transaction
            // is the target Account eligible for receiving?
            // are we sending to the same account? This is not allowed
            // if one account is saving and both must belong to the same user
            // the system allows transfer out of saving only to same user's checking

            validateAccount(sender, receiver);

            checkAccountOwnership(sender, receiver);

            executeBalanceAndUpdateIfRequired(amount, sender, receiver);

            // if transaction is complete, we need to create a Transaction Object and save it.

            Transaction transaction = Transaction.builder().amount(amount).sender(sender.getId())
                    .receiver(receiver.getId()).createDate(creationDate).message(message).build();
            return transactionRepository.save(transaction);
        }
        else {
            throw new UnderConstructionException("App is under construction. Please try again later");
        }
    }


    public void executeBalanceAndUpdateIfRequired(BigDecimal amount, Account sender, Account receiver) throws BalanceNotSufficientException {
        if(checkSenderBalance(sender, amount)){
            //update sender and receiver balance
            sender.setBalance( sender.getBalance().subtract(amount));
            receiver.setBalance(receiver.getBalance().add(amount));

        }else {
            throw new BalanceNotSufficientException("Balance is not enough for this transfer");
        }
    }

    private boolean checkSenderBalance(Account sender, BigDecimal amount) {

        return sender.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) >=0;

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





    }

    private void checkAccountOwnership(Account sender, Account receiver) {

        // write an if statement that checks if one of the account is a saving account
        // user of sender or receiver is not the same throw AccountOwnershipException

        if( (sender.getAccountType() == AccountType.SAVING || receiver.getAccountType()==AccountType.SAVING) && (!sender.getUserID().equals(receiver.getUserID()))){

            throw new AccountOwnershipException("Cannot transfer to or from someone else's Saving account");

        }else{
            // complete transaction
        }
    }

    private void findAccountById(UUID id) {

        accountRepository.findById(id);


    }

    @Override
    public List<Transaction> findAllTransaction() {

        return transactionRepository.findAll();
    }
}
