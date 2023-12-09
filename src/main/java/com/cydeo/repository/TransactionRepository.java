package com.cydeo.repository;


import com.cydeo.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // derived queries order first then limit
    @Query("select t from Transaction t order by t.createDate desc limit 10")
    List<Transaction> findLast10Transactions();

    @Query("select t from Transaction t where t.receiver.id = ?1 or t.sender.id =?1 order by t.createDate desc ")
    List<Transaction> findListById(Long id);

/*
    public static List<TransactionDTO> transactionDTOList = new ArrayList<>();

    public TransactionDTO save(TransactionDTO transactionDTO){
        transactionDTOList.add(transactionDTO);
        return transactionDTO;
    }

    public List<TransactionDTO> findAll(){
        return transactionDTOList;
    }

    public List<TransactionDTO> findTransactionListByAccountID(Long id){
        return transactionDTOList.stream()
                .filter(transactionDTO -> transactionDTO.getSender().equals(id) || transactionDTO.getReceiver().equals(id))
                .collect(Collectors.toList());
    }

 */
}


