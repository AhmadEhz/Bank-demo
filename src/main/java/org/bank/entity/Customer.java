package org.bank.entity;

import org.bank.base.entity.BaseEntity;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "customer")
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
}
