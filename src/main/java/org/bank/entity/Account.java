package org.bank.entity;

import org.bank.base.entity.BaseEntity;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Account extends BaseEntity {
    public Account(Customer customer) {
        this.customer = customer;
        balance = new BigDecimal(0);
    }

    public Account(Customer customer, double balance) {
        this.balance = BigDecimal.valueOf(balance);
        this.customer = customer;
    }

    public Account(double balance) {
        this.balance = BigDecimal.valueOf(balance);
    }

    public Account(Customer customer, double balance, String password) {
        this.customer = customer;
        this.balance = BigDecimal.valueOf(balance);
        this.password = password;
    }

    public Account() {
        balance = new BigDecimal(0);
    }

    @Id
    @GenericGenerator(name = "acc_gen", strategy = "org.bank.util.generator.AccountNumberGenerator")
    @GeneratedValue(generator = "acc_gen")
    private String accountNumber;
    private BigDecimal balance;
    @CreationTimestamp
    private LocalDate creationDate;
    private String password;

    @ManyToOne(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REMOVE,
            CascadeType.REFRESH
    })
    private Customer customer;
    @OneToOne(mappedBy = "account", cascade = {
            CascadeType.PERSIST,
            CascadeType.REMOVE,
            CascadeType.MERGE,
            CascadeType.REFRESH
    })
    private DebitCard debitCard;


    @OneToMany(mappedBy = "account", cascade = {
            CascadeType.PERSIST,
            CascadeType.REMOVE,
            CascadeType.MERGE,
            CascadeType.MERGE
    })
    private Set<Transaction> transactions;

    @PrePersist
    public void prePersist() {
        creationDate = LocalDate.now();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance.doubleValue();
    }

    public void setBalance(double balance) {
        this.balance = BigDecimal.valueOf(balance);
    }

    public void deposit(double amount) {
        balance = balance.add(BigDecimal.valueOf(amount));
    }

    public void withdraw(double amount) {
        balance = balance.subtract(BigDecimal.valueOf(amount));
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer account) {
        this.customer = account;
    }

    public DebitCard getCreditCards() {
        return debitCard;
    }

    public void setCreditCards(DebitCard debitCards) {
        this.debitCard = debitCards;
    }

    public DebitCard getDebitCard() {
        return debitCard;
    }

    public void setDebitCard(DebitCard debitCards) {
        this.debitCard = debitCards;
    }

    public Set<Transaction> getTransactions() {
        if (transactions == null)
            transactions = new HashSet<>();
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(Transaction transaction) {
        if (transactions == null)
            transactions = new HashSet<>();
        transactions.add(transaction);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountNumber, account.accountNumber) && Objects.equals(creationDate, account.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, creationDate);
    }

    @Override
    public String toString() {
        return "Account number: " + accountNumber
                + " | " + "Balance=" + balance +
                " | Creation date: " + creationDate;
    }
}
