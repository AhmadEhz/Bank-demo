package org.bank.service.impl;

import org.bank.base.service.BaseServiceImpl;
import org.bank.entity.*;
import org.bank.repository.AccountRepo;
import org.bank.repository.DebitCardRepo;
import org.bank.repository.TransactionRepo;
import org.bank.service.AccountService;
import org.bank.util.ApplicationContext;
import org.bank.util.Wage;
import org.bank.util.exception.InsufficientMoneyException;

import java.util.Optional;

public class AccountServiceImpl extends BaseServiceImpl<Account, String, AccountRepo> implements AccountService {
    private final TransactionRepo transactionRepo;
    private final DebitCardRepo debitCardRepo;

    public AccountServiceImpl(AccountRepo accountRepo) {
        super(accountRepo);
        transactionRepo = ApplicationContext.getTransactionRepo();
        debitCardRepo = ApplicationContext.getDebitCardRepo();
    }


    @Override
    public void cardToCard(Account originAccount, Account destinationAccount, double amount) {
        withdraw(originAccount, amount, Wage.CARD_TO_CARD);;
        deposit(destinationAccount, amount);

        Transaction originTransaction = new Transaction(originAccount, TransactionType.WITHDRAW, TransactionStatus.WAITING, amount + Wage.CARD_TO_CARD);
        Transaction destinationTransaction = new Transaction(destinationAccount, TransactionType.DEPOSIT, TransactionStatus.WAITING, amount);
        originAccount.addTransaction(originTransaction);
        destinationAccount.addTransaction(destinationTransaction);
        transactionRepo.create(originTransaction);
        transactionRepo.create(destinationTransaction);

        setCartToCart(originAccount, destinationAccount
                , originTransaction, destinationTransaction, amount);
    }

    @Override
    public double takeInventory(Account account) {
        repository.getEntityManager().getTransaction().begin();
        withdraw(account,Wage.INVENTORY);
        repository.update(account);
        transactionRepo.create(new Transaction(account, TransactionType.WITHDRAW, TransactionStatus.SUCCESS, Wage.INVENTORY));
        repository.getEntityManager().getTransaction().commit();
        return account.getBalance();
    }

    private void withdraw(Account account, double amount, double wage) {
        if (amount <= 0)
            throw new IllegalArgumentException("Amount is less than zero!");
        if (account.getBalance() < amount + wage)
            throw new InsufficientMoneyException("Insufficient account balance!");
        account.withdraw(amount + wage);
    }
    private void withdraw(Account account, double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Amount is less than zero!");
        if (account.getBalance() < amount)
            throw new InsufficientMoneyException("Insufficient account balance!");
        account.withdraw(amount);
    }

    private void setCartToCart(Account originAccount, Account destinationAccount, Transaction originTransaction, Transaction destinationTransaction, double amount) {
        try {
            repository.getEntityManager().getTransaction().begin();
            originTransaction.setStatus(TransactionStatus.SUCCESS);
            destinationTransaction.setStatus(TransactionStatus.SUCCESS);
            repository.update(originAccount);
            repository.update(destinationAccount);
            repository.getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            repository.getEntityManager().getTransaction().rollback();
            originTransaction.setStatus(TransactionStatus.FAILED);
            destinationTransaction.setStatus(TransactionStatus.FAILED);
            transactionRepo.update(originTransaction);
            transactionRepo.update(destinationTransaction);
            throw e;
        }
    }

    private void deposit(Account account, double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Amount is less than zero!");
        account.deposit(amount);
    }

    @Override
    public Optional<Account> load(DebitCard debitCard) {
        return repository.load(debitCard);
    }
}
