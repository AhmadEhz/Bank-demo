package org.bank.entity;

import org.bank.base.entity.BaseEntity;

import javax.persistence.*;

@Entity
public class Employee extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

}
