package com.cydeo.controller;

import com.cydeo.dto.AccountDTO;
import com.cydeo.dto.TransactionDTO;
import com.cydeo.service.AccountService;
import com.cydeo.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
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
        model.addAttribute("transactionDTO", new TransactionDTO());
        // user will set sender, receiver
        // need list of accounts for sender and receiver list of all accounts
        model.addAttribute("accounts", accountService.listAllActiveAccounts());
        // to populate the table, populate with the last 10 transactions
        // fill the table with the values in the last 10
        model.addAttribute("lastTenTransactions", transactionService.last10Transactions());

        return "transaction/make-transfer";
    }

    @PostMapping("/transfer")
    public String makeTransfer(@ModelAttribute("transactionDTO") @Valid TransactionDTO transactionDTO, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("accounts",accountService.listAllAccounts());
            model.addAttribute("lastTransactions",transactionService.last10Transactions());
            return "transaction/make-transfer";
        }

        //I have UUID of  accounts but I need to provide Account object.
        //I need to find the Accounts based on the ID that I have and use as a parameter to complete makeTransfer method.
        AccountDTO sender = accountService.retrieveByID(transactionDTO.getSender().getId());
        AccountDTO receiver = accountService.retrieveByID(transactionDTO.getReceiver().getId());
        transactionService.makeTransfer(
                sender,
                receiver,
                transactionDTO.getAmount(),
                new Date(),
                transactionDTO.getMessage());

        return "redirect:/make-transfer";
    }

    /*
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

     */

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
