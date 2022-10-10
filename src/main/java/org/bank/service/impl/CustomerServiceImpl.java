package org.bank.service.impl;

import org.bank.base.service.BaseServiceImpl;
import org.bank.entity.Account;
import org.bank.entity.Customer;
import org.bank.repository.CustomerRepo;
import org.bank.service.CustomerService;
import org.bank.util.Values;

import javax.persistence.NoResultException;
import java.util.Optional;
import java.util.Set;

public class CustomerServiceImpl extends BaseServiceImpl<Customer, Long, CustomerRepo> implements CustomerService {

    public CustomerServiceImpl(CustomerRepo customerRepo) {
        super(customerRepo);
    }

    @Override
    public void save(Customer customer) {
        checkPhoneNumber(customer.getPhoneNumber());
        checkNationalCode(customer.getNationalCode());
        super.save(customer);
    }

    private void checkPhoneNumber(String phoneNumber) {
        if (!phoneNumber.matches(Values.PHONE_NUMBER_REGEX))
            throw new IllegalArgumentException("Phone number format is incorrect!");
    }

    private void checkNationalCode(String nationalCode) {
        if (!nationalCode.matches(Values.NATIONAL_CODE_REGEX))
            throw new IllegalArgumentException("National code format is incorrect!");
    }


    @Override
    public Optional<Customer> load(String nationalCode) {
        checkNationalCode(nationalCode);
        try {
            return repository.read(nationalCode);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
    @Override
    public Set<Account> getAccounts(Customer customer) {
        return repository.getAccounts(customer);
    }
}
