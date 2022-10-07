package org.bank.repository.impl;

import org.bank.base.repository.BaseRepositoryImpl;
import org.bank.entity.Account;
import org.bank.repository.AccountRepo;

import javax.persistence.EntityManager;
import java.util.Optional;

public class AccountRepoImpl extends BaseRepositoryImpl<Account, String> implements AccountRepo {
    public AccountRepoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Account> getEntityClass() {
        return Account.class;
    }


}
