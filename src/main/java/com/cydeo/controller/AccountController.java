package com.cydeo.controller;

import com.cydeo.model.Account;
import com.cydeo.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
