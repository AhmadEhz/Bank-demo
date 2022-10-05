package org.bank.entity;

import org.bank.base.entity.BaseEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Customer extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany(mappedBy = "customer")
    private Set<Account> accounts;

}
