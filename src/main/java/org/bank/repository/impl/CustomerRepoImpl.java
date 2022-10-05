package org.bank.repository.impl;

import org.bank.base.repository.BaseRepositoryImpl;
import org.bank.entity.Customer;
import org.bank.repository.CustomerRepo;

import javax.persistence.EntityManager;

public class CustomerRepoImpl extends BaseRepositoryImpl<Customer,Long> implements CustomerRepo {
    public CustomerRepoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Customer> getEntityClass() {
        return Customer.class;
    }

}
