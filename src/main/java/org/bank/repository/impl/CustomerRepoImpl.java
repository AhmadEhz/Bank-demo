package org.bank.repository.impl;

import org.bank.base.repository.BaseRepositoryImpl;
import org.bank.entity.Account;
import org.bank.entity.Customer;
import org.bank.repository.CustomerRepo;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomerRepoImpl extends BaseRepositoryImpl<Customer, Long> implements CustomerRepo {
    public CustomerRepoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Customer> getEntityClass() {
        return Customer.class;
    }

    @Override
    public Optional<Customer> read(String nationalCode) {
        try {
            return Optional.ofNullable(entityManager.createQuery("from Customer where nationalCode = :nc"
                    , getEntityClass()).setParameter("nc", nationalCode).getSingleResult());
        }catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Set<Account> getAccounts(Customer customer) {
        try {
            return new HashSet<>(entityManager.createQuery("""
                        select acc from Account as acc
                        join Customer as cu on acc.customer = cu
                        where cu.firstName = :fn and cu.lastName = :ln
                        and cu.nationalCode = :nc and cu.phoneNumber = :pn""", Account.class)
                .setParameter("fn", customer.getFirstName())
                .setParameter("ln", customer.getLastName())
                .setParameter("nc", customer.getNationalCode())
                .setParameter("pn", customer.getPhoneNumber()).getResultList());
        }catch (NoResultException e) {
            return new HashSet<>();
        }
    }
}
