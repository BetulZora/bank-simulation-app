package com.cydeo.repository;

import com.cydeo.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
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


