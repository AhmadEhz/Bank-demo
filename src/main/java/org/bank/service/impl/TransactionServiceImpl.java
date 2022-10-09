package org.bank.service.impl;

import org.bank.base.service.BaseServiceImpl;
import org.bank.entity.Transaction;
import org.bank.entity.TransactionStatus;
import org.bank.repository.TransactionRepo;
import org.bank.repository.impl.TransactionRepoImpl;
import org.bank.service.TransactionService;
import org.bank.util.ApplicationContext;

import javax.persistence.EntityManager;

public class TransactionServiceImpl extends BaseServiceImpl<Transaction,Long, TransactionRepo> implements TransactionService {
    public TransactionServiceImpl(TransactionRepo transactionRepo) {
        super(transactionRepo);
    }
    public void update(Transaction transaction, TransactionStatus status) {
        transaction.setStatus(status);
        update(transaction);
    }
    public void save(Transaction transaction, TransactionStatus status) {
        transaction.setStatus(status);
        save(transaction);
    }
    @Override
    public void saveOrUpdate(Transaction transaction) {
        repository.createOrUpdate(transaction);
    }
}
