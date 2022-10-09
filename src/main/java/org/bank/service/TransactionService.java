package org.bank.service;

import org.bank.base.service.BaseService;
import org.bank.entity.Transaction;
import org.bank.entity.TransactionStatus;
import org.bank.repository.TransactionRepo;

public interface TransactionService extends BaseService<Transaction,Long,TransactionRepo> {
    void update(Transaction transaction, TransactionStatus status);
    void save(Transaction transaction, TransactionStatus status);
    void saveOrUpdate(Transaction transaction);
}
