package org.bank.entity;

import org.bank.base.entity.BaseEntity;

import javax.persistence.*;

@Entity
public class Employee extends Person {
    public Employee(String firstName, String lastName, String address, String nationalCode, String phoneNumber) {
        super(firstName, lastName, address, nationalCode, phoneNumber);
    }

    public Employee() {
    }

}
