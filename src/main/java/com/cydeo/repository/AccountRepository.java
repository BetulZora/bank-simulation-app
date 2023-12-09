package com.cydeo.repository;

import com.cydeo.dto.AccountDTO;
import com.cydeo.entity.Account;
import com.cydeo.enums.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByAccountStatus(AccountStatus accountStatus);

    @Query("select a from Account a where a.accountStatus = 'ACTIVE'")
    List<Account> allActiveAccounts();

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
