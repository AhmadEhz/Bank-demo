package org.bank.entity;

import org.bank.base.entity.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Transaction extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Account account;
    @Enumerated(value = EnumType.STRING)
    private TransactionType transactionType;
    private LocalDate date;

}
