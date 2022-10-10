package org.bank.repository.impl;

import org.bank.base.repository.BaseRepositoryImpl;
import org.bank.entity.Account;
import org.bank.entity.DebitCard;
import org.bank.repository.AccountRepo;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
    public Optional<Account> read(DebitCard debitCard) {
        try {
            return Optional.ofNullable(entityManager.createQuery("""
                        select acc from Account as acc
                        join DebitCard as dc on acc.debitCard.cardNumber = dc
                        where dc.cardNumber = :cardNum and dc.cvv2 = :cvv2""", Account.class)
                .setParameter("cardNum", debitCard.getCardNumber())
                .setParameter("cvv2", debitCard.getCvv2()).getSingleResult());
        }catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Account> read(String accountNumber, String password) {
        try {
            return Optional.ofNullable(entityManager.createQuery("""
                from Account 
                where accountNumber = :accNum and password = :pass""", Account.class)
                .setParameter("accNum",accountNumber)
                .setParameter("pass", password).getSingleResult());
        }catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
