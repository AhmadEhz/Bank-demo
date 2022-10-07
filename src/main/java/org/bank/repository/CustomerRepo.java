package org.bank.repository;

import org.bank.base.repository.BaseRepository;
import org.bank.entity.Customer;

import java.util.Optional;

public interface CustomerRepo extends BaseRepository<Customer, Long> {
    Optional<Customer> read(String nationalCode);
}
