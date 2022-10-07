package org.bank.entity;

import org.bank.base.entity.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class Account extends BaseEntity {
    @Id
    private String accountNumber;
    private double balance;
    private LocalDate creationDate;
    @ManyToOne
    private Account account;
    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private DebitCard debitCards;
    
    @OneToMany(mappedBy = "account")
    private Set<Transaction> transactions;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public DebitCard getCreditCards() {
        return debitCards;
    }

    public void setCreditCards(DebitCard debitCards) {
        this.debitCards = debitCards;
    }

}
