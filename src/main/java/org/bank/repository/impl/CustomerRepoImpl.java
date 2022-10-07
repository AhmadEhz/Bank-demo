package org.bank.repository.impl;

import org.bank.base.repository.BaseRepositoryImpl;
import org.bank.entity.Account;
import org.bank.entity.Customer;
import org.bank.repository.CustomerRepo;

import javax.persistence.EntityManager;
import java.util.Optional;

public class CustomerRepoImpl extends BaseRepositoryImpl<Customer,Long> implements CustomerRepo {
    public CustomerRepoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Customer> getEntityClass() {
        return Customer.class;
    }
    @Override
    public Optional<Customer> read(String nationalCode) {
        return Optional.ofNullable(entityManager.createQuery("from Customer where nationalCode = :nc"
                , getEntityClass()).setParameter("nc", nationalCode).getSingleResult());
    }
}
