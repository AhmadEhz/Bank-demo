package org.bank.entity;

import org.bank.base.entity.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Transaction extends BaseEntity {
    public Transaction() {

    }

    public Transaction(Account account, TransactionType type, TransactionStatus status, LocalDate date) {
        this.account = account;
        this.type = type;
        this.status = status;
        this.date = date;
    }

    public Transaction(TransactionType type, TransactionStatus status, LocalDate date) {
        this.type = type;
        this.status = status;
        this.date = date;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Account account;
    @Enumerated(value = EnumType.STRING)
    private TransactionType type;
    private TransactionStatus status;
    private LocalDate date;

    public long getId() {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
