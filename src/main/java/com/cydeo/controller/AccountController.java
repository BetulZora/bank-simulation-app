package com.cydeo.controller;


import com.cydeo.enums.AccountType;
import com.cydeo.dto.AccountDTO;
import com.cydeo.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import javax.validation.Valid;

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
        model.addAttribute("emptyaccount", new AccountDTO());
        // provide an account type enum info to populate the dropdown\
        model.addAttribute("accountTypes", AccountType.values());

        return "account/create-account";
    }

    @PostMapping("/create")
    public String saveCreatedAccount(@Valid@ModelAttribute("emptyaccount") AccountDTO filledAccountDTO,
                                     BindingResult bindingResult,
                                     Model model){
        if(bindingResult.hasErrors()){
            // provide information needed in the redirected page
            // DO NOT provide a brand-new object
            model.addAttribute("accountTypes", AccountType.values());
            // redirect to the appropriate page
            return "account/create-account";
        }
        System.out.println("filledAccount = " + filledAccountDTO);
        accountService.createNewAccount(filledAccountDTO);
        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        System.out.println("id = " + id);
        accountService.deleteAccount(id);

        return "redirect:/index";
    }
    @GetMapping("/activate/{id}")
    public String activate(@PathVariable("id") Long id) {
        accountService.activateAccount(id);

        return "redirect:/index";
    }

    }
