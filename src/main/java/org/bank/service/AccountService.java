package org.bank.service;

import org.bank.base.service.BaseService;
import org.bank.entity.Account;
import org.bank.repository.AccountRepo;

import java.util.Optional;

public interface AccountService extends BaseService<Account, String, AccountRepo> {
    Optional<Account> load(String nationalCode);

}
