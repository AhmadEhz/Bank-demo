package org.bank.repository;

import org.bank.base.repository.BaseRepository;
import org.bank.entity.Transaction;

public interface TransactionRepo extends BaseRepository<Transaction,Long> {
    void createOrUpdate(Transaction transaction);
}
