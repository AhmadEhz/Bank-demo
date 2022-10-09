package org.bank.entity;

import org.bank.base.entity.BaseEntity;

import javax.persistence.*;

@MappedSuperclass
public abstract class Person extends BaseEntity {
    protected Person() {

    }

    protected Person(String firstName, String lastName, String address, String nationalCode, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.nationalCode = nationalCode;
        this.phoneNumber = phoneNumber;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    private String address;
    @Column(unique = true)
    private String nationalCode;
    private String phoneNumber;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
