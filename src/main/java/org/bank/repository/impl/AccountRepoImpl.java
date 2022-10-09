package org.bank.repository.impl;

import org.bank.base.repository.BaseRepositoryImpl;
import org.bank.entity.Account;
import org.bank.entity.DebitCard;
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

    @Override
    public Optional<Account> load(DebitCard debitCard) {
        return Optional.of(entityManager.createQuery("""
                select Account from Account as acc
                join DebitCard as dc on acc.debitCard = dc.cardNumber
                where dc.cardNumber = :cardNum and dc.cvv2 = :cvv2""", Account.class).getSingleResult());
    }
}
