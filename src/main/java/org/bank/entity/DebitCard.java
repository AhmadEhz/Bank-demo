package org.bank.entity;

import org.bank.base.entity.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class DebitCard extends BaseEntity {
    public DebitCard() {
    }

    public DebitCard(Account account, int cvv2, LocalDate expirationDate) {
        this.account = account;
        this.cvv2 = cvv2;
        this.expirationDate = expirationDate;
    }


    @Id
    private String cardNumber;
    @OneToOne
    private Account account;
    @Column(nullable = false)
    private int cvv2;
    @Column(nullable = false)
    private LocalDate expirationDate;

}
