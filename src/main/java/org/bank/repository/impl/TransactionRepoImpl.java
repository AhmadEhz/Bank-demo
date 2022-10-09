package org.bank.repository.impl;

import org.bank.base.repository.BaseRepositoryImpl;
import org.bank.entity.Transaction;
import org.bank.repository.TransactionRepo;

import javax.persistence.EntityManager;

public class TransactionRepoImpl extends BaseRepositoryImpl<Transaction,Long> implements TransactionRepo {

    public TransactionRepoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Transaction> getEntityClass() {
        return Transaction.class;
    }

    @Override
    public void createOrUpdate(Transaction transaction) {
        if(transaction.getId() ==null)
            create(transaction);

        else update(transaction);
    }
}
