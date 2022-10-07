package org.bank.service.impl;

import org.bank.base.repository.BaseRepository;
import org.bank.base.service.BaseServiceImpl;
import org.bank.entity.Account;
import org.bank.repository.AccountRepo;
import org.bank.repository.impl.AccountRepoImpl;
import org.bank.service.AccountService;

import javax.persistence.EntityManager;
import java.util.Optional;

public class AccountServiceImpl extends BaseServiceImpl<Account,String, AccountRepo> implements AccountService {

    public AccountServiceImpl(EntityManager entityManager) {
        super(new AccountRepoImpl(entityManager));
    }
}
