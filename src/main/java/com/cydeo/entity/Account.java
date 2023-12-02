package com.cydeo.entity;

import com.cydeo.enums.AccountStatus;
import com.cydeo.enums.AccountType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal balance;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(columnDefinition = "TIMESTAMP")
    private Date creationDate;

    private Long userID;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
}
