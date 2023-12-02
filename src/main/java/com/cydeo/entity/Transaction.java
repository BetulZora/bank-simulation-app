package com.cydeo.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.mapping.ToOne;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Account sender;
    @ManyToOne
    private Account receiver;
    private BigDecimal amount;

    private String message;

    @Column(columnDefinition = "TIMESTAMP")
    private Date createDate;
}
