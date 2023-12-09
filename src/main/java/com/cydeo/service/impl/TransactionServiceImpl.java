package com.cydeo.service.impl;

import com.cydeo.enums.AccountType;
import com.cydeo.exceptions.BadRequestException;
import com.cydeo.exceptions.AccountOwnershipException;
import com.cydeo.exceptions.BalanceNotSufficientException;
import com.cydeo.exceptions.UnderConstructionException;
import com.cydeo.dto.AccountDTO;
import com.cydeo.dto.TransactionDTO;
import com.cydeo.mapper.TransactionMapper;
import com.cydeo.repository.AccountRepository;
import com.cydeo.repository.TransactionRepository;
import com.cydeo.service.AccountService;
import com.cydeo.service.TransactionService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class TransactionServiceImpl implements TransactionService {

    @Value("${under_construction}")
    private boolean underConstruction;
    // Use the @Value annotation to get values from the properties file


    // best practice to work with Service layers
    //private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(AccountService accountService, TransactionMapper transactionMapper, TransactionRepository transactionRepository) {
        this.accountService = accountService;
        this.transactionMapper = transactionMapper;
        this.transactionRepository = transactionRepository;
    }

    @SneakyThrows
    @Override
    public TransactionDTO makeTransfer(AccountDTO sender, AccountDTO receiver, BigDecimal amount, Date creationDate, String message) {

        //make transaction if app is not under construction
        if(!underConstruction) {

            validateAccount(sender, receiver);

            checkAccountOwnership(sender, receiver);

            executeBalanceAndUpdateIfRequired(amount, sender, receiver);

            // if transaction is complete, we need to create a Transaction Object and save it.

            TransactionDTO transactionDTO = new TransactionDTO() ;
            return transactionRepository.save(transactionDTO);
        }
        else {
            throw new UnderConstructionException("App is under construction. Please try again later");
        }
    }


    public void executeBalanceAndUpdateIfRequired(BigDecimal amount, AccountDTO sender, AccountDTO receiver) throws BalanceNotSufficientException {
        if(checkSenderBalance(sender, amount)){
            //update sender and receiver balance
            sender.setBalance( sender.getBalance().subtract(amount));
            receiver.setBalance(receiver.getBalance().add(amount));

        }else {
            throw new BalanceNotSufficientException("Balance is not enough for this transfer");
        }
    }

    private boolean checkSenderBalance(AccountDTO sender, BigDecimal amount) {

        return sender.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) >=0;

    }

    private void validateAccount(AccountDTO sender, AccountDTO receiver) {

        // is the target Account eligible for receiving and the sender too?

        if(sender==null || receiver == null){
            throw new BadRequestException("Sender or Receiver cannot be null");
        }
        // are we sending to the same account? This is not allowed
        if(sender.getId().equals( receiver.getId())){
            throw new BadRequestException("Sender and Receiver accounts need to be different");
        }

    }

    private void checkAccountOwnership(AccountDTO sender, AccountDTO receiver) {

        // write an if statement that checks if one of the account is a saving account
        // user of sender or receiver is not the same throw AccountOwnershipException

        if( (sender.getAccountType() == AccountType.SAVING || receiver.getAccountType()==AccountType.SAVING) && (!sender.getUserID().equals(receiver.getUserID()))){

            throw new AccountOwnershipException("Cannot transfer to or from someone else's Saving account");

        }else{
            // complete transaction
        }
    }

    private void findAccountById(Long id) {

        accountRepository.findById(id);


    }

    @Override
    public List<TransactionDTO> findAllTransaction() {

        return transactionRepository.findAll().stream().map(transactionMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> last10Transactions() {
        return transactionRepository.findLast10Transactions().stream().map(transactionMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> findTransactionListById(Long id) {
        return transactionRepository.findListById(id).stream().map(transactionMapper::convertToDTO).collect(Collectors.toList());
    }
}
