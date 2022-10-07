package org.bank.service.impl;

import org.bank.base.service.BaseServiceImpl;
import org.bank.entity.Customer;
import org.bank.repository.CustomerRepo;
import org.bank.repository.impl.CustomerRepoImpl;
import org.bank.service.CustomerService;

import javax.persistence.EntityManager;
import java.util.Optional;

public class CustomerServiceImpl extends BaseServiceImpl<Customer, Long, CustomerRepo> implements CustomerService {
    CustomerRepo repository;


    public CustomerServiceImpl(EntityManager entityManager) {
        super(new CustomerRepoImpl(entityManager));
        repository = new CustomerRepoImpl(entityManager);
    }


    @Override
    public Optional<Customer> load(String nationalCode) {
        return repository.read(nationalCode);
    }

}
