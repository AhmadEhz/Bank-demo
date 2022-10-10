package org.bank.repository;

import org.bank.base.repository.BaseRepository;
import org.bank.entity.Account;
import org.bank.entity.Customer;

import java.util.Optional;
import java.util.Set;

public interface CustomerRepo extends BaseRepository<Customer, Long> {
    Optional<Customer> read(String nationalCode);

    Set<Account> getAccounts(Customer customer);
}
