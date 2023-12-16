package com.cydeo.converter;

import com.cydeo.dto.AccountDTO;
import com.cydeo.service.AccountService;
import org.springframework.core.convert.converter.Converter;

public class AccountConverter implements Converter <String, AccountDTO> {


    private final AccountService accountService;

    public AccountConverter(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public AccountDTO convert(String source) {
        if(source == null || source.equals("")){
            return null;
        }
        return accountService.retrieveByID(Long.parseLong(source));
    }
}
