package com.cydeo.repository;

import com.cydeo.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    /*
    public static List<AccountDTO> accountDTOList = new ArrayList<>();

    public AccountDTO save (AccountDTO accountDTO){
        accountDTOList.add(accountDTO);
        return accountDTO;
    }

    public List<AccountDTO> findall() {
        return accountDTOList;

    }

    public AccountDTO findById(Long id) {


        return accountDTOList.stream().filter(account -> account.getId().equals(id))
                .findAny().orElseThrow(()-> new RecordNotFoundException("Account does not exist"));

    }

     */
}
