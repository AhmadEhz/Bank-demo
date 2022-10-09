package org.bank.entity;


import javax.persistence.*;
import java.util.Objects;

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

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Boss boss = (Boss) o;
        return Objects.equals(branch, boss.branch) && Objects.equals(this.getId(), boss.getId())
                && Objects.equals(this.getFirstName(), boss.getFirstName()) && Objects.equals(this.getLastName(), boss.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(branch, getId(), getFirstName(), getLastName());
    }

    @Override
    public String toString() {
        return "ID: " + getId()
                + " | First name: " + getFirstName()
                + " | Last name: " + getLastName()
                + " | National code: " + getNationalCode()
                + " | Address" + getAddress()
                + " | Branch " + getBranch();
    }
}
