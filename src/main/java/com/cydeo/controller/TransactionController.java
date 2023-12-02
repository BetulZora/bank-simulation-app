package com.cydeo.controller;

import com.cydeo.dto.TransactionDTO;
import com.cydeo.service.AccountService;
import com.cydeo.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Controller
public class TransactionController {

    private final AccountService accountService;
    private final TransactionService transactionService;

    public TransactionController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @GetMapping("/make-transfer")
    public String getMakeTransfer(Model model){

        // need an empty transaction object to populate
        model.addAttribute("transaction", new TransactionDTO());
        // user will set sender, receiver
        // need list of accounts for sender and receiver list of all accounts
        model.addAttribute("accounts", accountService.listAllAccounts());
        // to populate the table, populate with the last 10 transactions
        // fill the table with the values in the last 10
        model.addAttribute("lastTenTransactions", transactionService.last10Transactions());

        return "transaction/make-transfer";
    }

    @PostMapping("/completeTransaction")
    public String completeTransaction(TransactionDTO filledTransactionDTO){
        transactionService.makeTransfer(
                accountService.retrieveByID(filledTransactionDTO.getSender().getId()),
                accountService.retrieveByID(filledTransactionDTO.getReceiver().getId()),
                filledTransactionDTO.getAmount(),
                new Date(),
                filledTransactionDTO.getMessage()
        );

        return "redirect:/make-transfer";

    }

    // write a method that gets the account ID  from index.html and prints on the console

    @GetMapping("/transaction/{id}")
    public String transaction(@PathVariable("id") Long id, Model model){
        System.out.println("id = " + id);
        // create the method that finds transactions by the id
        // findTransactionListByID

        model.addAttribute("transactions", transactionService.findTransactionListById(id));
        return "transaction/transactions";
    }


}
