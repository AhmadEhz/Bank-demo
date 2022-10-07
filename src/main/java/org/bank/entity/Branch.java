package org.bank.entity;

import org.bank.base.entity.BaseEntity;

import javax.persistence.*;

@Entity

public class Branch extends BaseEntity {
    public Branch() {
    }

    public Branch(String address) {
        this.address = address;
    }

    public Branch(String address, Boss boss) {
        this.address = address;
        this.boss = boss;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String address;
    @OneToOne(mappedBy = "branch")
    private Boss boss;
}
