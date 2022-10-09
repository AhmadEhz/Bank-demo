package org.bank.entity;

import org.bank.base.entity.BaseEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Employee extends Person {
    public Employee(String firstName, String lastName, String address, String nationalCode, String phoneNumber) {
        super(firstName, lastName, address, nationalCode, phoneNumber);
    }

    public Employee() {
    }

    @Override
    public String toString() {
        return super.toString();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Boss boss = (Boss) o;
        return Objects.equals(this.getId(), boss.getId())
                && Objects.equals(this.getFirstName(), boss.getFirstName()) && Objects.equals(this.getLastName(), boss.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName());
    }
}
