package org.bank.entity;

import org.bank.base.entity.BaseEntity;

import javax.persistence.*;

@Entity

public class Branch extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String address;
    @OneToOne(mappedBy = "branch")
    private Boss boss;
}
