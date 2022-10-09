package org.bank.entity;

import org.bank.base.entity.BaseEntity;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Transaction extends BaseEntity {
    public Transaction() {

    }

    public Transaction(Account account, TransactionType type, TransactionStatus status,double amount) {
        this.account = account;
        this.type = type;
        this.status = status;
        this.amount= amount;
    }

    public Transaction(TransactionType type, TransactionStatus status, double amount) {
        this.type = type;
        this.status = status;
        this.amount = amount;
    }

    public Transaction(TransactionType type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Account account;
    @Enumerated(value = EnumType.STRING)
    private TransactionType type;
    @Enumerated(value = EnumType.STRING)
    private TransactionStatus status;
    private double amount;
    @CreationTimestamp
    private LocalDateTime time;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime date) {
        this.time = date;
    }
}
