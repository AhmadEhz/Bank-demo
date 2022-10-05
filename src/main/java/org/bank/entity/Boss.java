package org.bank.entity;


import javax.persistence.*;

@Entity
public class Boss extends Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    private Branch branch;
}
