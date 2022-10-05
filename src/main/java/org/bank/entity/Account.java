package org.bank.entity;

import org.bank.base.entity.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Account extends BaseEntity {
    @Id
    private String accountNumber;
    private double balance;
    private LocalDate creationDate;
    @ManyToOne
    private Account account;
    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private CreditCard creditCards;

}
