package org.bank.service.impl;

import org.bank.base.service.BaseServiceImpl;
import org.bank.entity.Customer;
import org.bank.repository.CustomerRepo;
import org.bank.repository.impl.CustomerRepoImpl;
import org.bank.service.CustomerService;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;

public class CustomerServiceImpl extends BaseServiceImpl<Customer, Long, CustomerRepo> implements CustomerService {

    public CustomerServiceImpl(CustomerRepo customerRepo) {
        super(customerRepo);
    }


    @Override
    public Optional<Customer> load(String nationalCode) {
        try {
            return repository.read(nationalCode);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
