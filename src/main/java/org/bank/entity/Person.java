package org.bank.entity;

import org.bank.base.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Person extends BaseEntity {
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    private String address;
    @Column(unique = true)
    private String nationalCode;
    private String phoneNumber;
}
