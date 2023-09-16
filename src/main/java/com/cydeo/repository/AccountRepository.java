package com.cydeo.repository;

import com.cydeo.exceptions.RecordNotFoundException;
import com.cydeo.model.Account;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class AccountRepository {

    public static List<Account> accountList = new ArrayList<>();

    public Account save (Account account){
        accountList.add(account);
        return account;
    }

    public List<Account> findall() {
        return accountList;

    }

    public Account findById(UUID id) {

        //Task
        // write a method that find the account inside the list
        // if not throw RecordNotFoundException
/*
        List<Account> list = findall().stream().filter(account -> account.getId().equals(id)).collect(Collectors.toList());
        if(list.isEmpty()){
            throw RecordNotFoundException("Account does not exist");
        } else {
            return list.get(0);
        }

        return null;
*/
        return accountList.stream().filter(account -> account.getId().equals(id))
                .findAny().orElseThrow(()-> new RecordNotFoundException("Account does not exist"));

    }
}
