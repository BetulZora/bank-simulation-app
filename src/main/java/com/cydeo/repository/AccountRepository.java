package com.cydeo.repository;

import com.cydeo.dto.AccountDTO;
import com.cydeo.exceptions.RecordNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountRepository {

    public static List<AccountDTO> accountDTOList = new ArrayList<>();

    public AccountDTO save (AccountDTO accountDTO){
        accountDTOList.add(accountDTO);
        return accountDTO;
    }

    public List<AccountDTO> findall() {
        return accountDTOList;

    }

    public AccountDTO findById(Long id) {

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
        return accountDTOList.stream().filter(account -> account.getId().equals(id))
                .findAny().orElseThrow(()-> new RecordNotFoundException("Account does not exist"));

    }
}
