package org.bank.entity;


import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class Customer extends Person {
    public Customer() {
    }

    public Customer(String firstName, String lastName, String address, String nationalCode, String phoneNumber, Set<Account> accounts) {
        super(firstName, lastName, address, nationalCode, phoneNumber);
        this.accounts = accounts;
    }

    public Customer(String firstName, String lastName, String address, String nationalCode, String phoneNumber) {
        super(firstName, lastName, address, nationalCode, phoneNumber);
    }

    @OneToMany(mappedBy = "customer", cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REMOVE
    })
    private Set<Account> accounts;


    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
    public void addAccount(Account account) {
        accounts.add(account);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(getId(), customer.getId())
                && Objects.equals(getNationalCode(), customer.getNationalCode())
                && Objects.equals(getFirstName(), customer.getFirstName())
                && Objects.equals(getLastName(), customer.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(accounts,getId(),getNationalCode(),getFirstName(),getLastName());

    }

    @Override
    public String toString() {
        return "ID: " + getId()
                + " | First name: " + getFirstName()
                + " | Last name: " + getLastName()
                + " | National code: " + getNationalCode()
                + " | Phone number: " + getPhoneNumber();
    }
}
