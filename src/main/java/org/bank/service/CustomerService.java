package org.bank.service;

import org.bank.base.service.BaseService;
import org.bank.entity.Account;
import org.bank.entity.Customer;
import org.bank.repository.CustomerRepo;

import java.util.Optional;
import java.util.Set;

public interface CustomerService extends BaseService<Customer, Long, CustomerRepo> {
    Optional<Customer> load(String nationalCode);


    Set<Account> getAccounts(Customer customer);
}
