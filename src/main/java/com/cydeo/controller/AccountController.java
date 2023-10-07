package com.cydeo.controller;


import com.cydeo.enums.AccountType;
import com.cydeo.model.Account;
import com.cydeo.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Controller
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService){
        this.accountService=accountService;
    }

    @GetMapping("/index")
    public String getIndexPage(Model model){
        /*
        Write a method that returns index.html including account list information
        endpoint: /index

         */
        model.addAttribute("accountList", accountService.listAllAccounts());
        return "account/index";
    }

    @GetMapping("/create-form")
    public String createAccount(Model model){
        // provide an empty account object
        model.addAttribute("emptyaccount", Account.builder().build());
        // provide an account type enum info to populate the dropdown\
        model.addAttribute("accountTypes", AccountType.values());

        return "account/create-account";
    }

    @PostMapping("/create")
    public String saveCreatedAccount(@ModelAttribute("emptyaccount") Account filledAccount){
        System.out.println("filledAccount = " + filledAccount);
        accountService.createNewAccount(filledAccount.getBalance(),new Date(),filledAccount.getAccountType(), filledAccount.getUserID());
        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") UUID id){
        System.out.println("id = " + id);
        accountService.deleteByID(id);

        return "redirect:/index";
    }
    @GetMapping("/activate/{id}")
    public String activate(@PathVariable("id") UUID id) {
        accountService.activateByID(id);

        return "redirect:/index";
    }

    }
