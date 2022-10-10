package org.bank.entity;

import org.bank.base.entity.BaseEntity;
import org.bank.util.Utility;
import org.bank.util.Values;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class DebitCard extends BaseEntity {
    public DebitCard() {
    }

    public DebitCard(Account account) {
        this.account = account;
    }

    public DebitCard(String cardNumber, int cvv2) {
        this.cardNumber = cardNumber;
        this.cvv2 = cvv2;
    }

    private static final long baseCardNumber = 6063_2710_1010_1000L;

    @Id
    @GenericGenerator(name = "card_num_gen", strategy = "org.bank.util.generator.CardNumberGenerator")
    @GeneratedValue(generator = "card_num_gen")
    private String cardNumber;
    @OneToOne(cascade = {
            CascadeType.PERSIST,
            CascadeType.REMOVE,
            CascadeType.MERGE,
            CascadeType.REFRESH
    })
    private Account account;
    @Column(nullable = false)
    private int cvv2;
    @Column(nullable = false)
    private LocalDate expirationDate;
    private boolean isSuspended;
    @Column(name = "incorrect_password")
    private int numberOfIncorrectPasswordEntered;

    @PrePersist
    public void prePersist() {
        cvv2 = Utility.randomGenerator(10000);
        LocalDate now = LocalDate.now();
        this.expirationDate = LocalDate.of(now.getYear(), now.getMonth(), now.lengthOfMonth()).plusMonths(Values.EXPIRATION_CARD_TIME);
        //Set last day of month in expirationDate.
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public int getCvv2() {
        return cvv2;
    }

    public void setCvv2(int cvv2) {
        this.cvv2 = cvv2;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
    public double getBalance () {
        return account.getBalance();
    }
    public void setBalance(double amount) {
        account.setBalance(amount);
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    public void setSuspended(boolean suspended) {
        isSuspended = suspended;
    }

    public int getNumberOfIncorrectPasswordEntered() {
        return numberOfIncorrectPasswordEntered;
    }

    public void setNumberOfIncorrectPasswordEntered(int numberOfIncorrectPasswordEntered) {
        this.numberOfIncorrectPasswordEntered = numberOfIncorrectPasswordEntered;
    }
    public void increaseNumberOfIncorrectPasswordEntered() {
        numberOfIncorrectPasswordEntered++;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DebitCard debitCard = (DebitCard) o;
        return Objects.equals(cardNumber, debitCard.cardNumber) && Objects.equals(account, debitCard.account) && Objects.equals(expirationDate, debitCard.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber, account, expirationDate);
    }

    @Override
    public String toString() {
        return "Card number: " + cardNumber
                + " | Account: " + account +
                " | Expiration date:" + expirationDate;
    }
}
