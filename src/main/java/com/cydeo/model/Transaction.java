package com.cydeo.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class Transaction {

    //TODO: complete validation as homework

    private UUID sender; // Aligning with Account
    private UUID receiver;
    private BigDecimal amount;
    private String message;
    private Date createDate;

}
