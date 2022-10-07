package org.bank.entity;


import javax.persistence.*;

@Entity
public class Boss extends Employee {
    public Boss() {

    }
    public Boss(String firstName, String lastName, String address, String nationalCode, String phoneNumber, Branch branch) {
        super(firstName, lastName, address, nationalCode, phoneNumber);
        this.branch = branch;
    }

    @OneToOne
    private Branch branch;
}
