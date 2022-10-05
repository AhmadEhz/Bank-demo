package org.bank.entity;

import org.bank.base.entity.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class CreditCard extends BaseEntity {
    @Id
    private String cardNumber;
    @OneToOne
    private Account account;
    @Column(nullable = false)
    private int cvv2;
    @Column(nullable = false)
    private LocalDate expirationDate;
}
