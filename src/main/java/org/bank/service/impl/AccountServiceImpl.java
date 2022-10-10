package org.bank.service.impl;

import org.bank.base.service.BaseServiceImpl;
import org.bank.entity.*;
import org.bank.repository.AccountRepo;
import org.bank.repository.DebitCardRepo;
import org.bank.repository.TransactionRepo;
import org.bank.service.AccountService;
import org.bank.util.ApplicationContext;
import org.bank.util.Values;
import org.bank.util.exception.EntityNotFoundException;
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
    public void save(Account account) {
        if (!account.getPassword().matches(Values.PASSWORD_REGEX))
            throw new IllegalArgumentException("Your password must be at least one uppercase, one lowercase and one number!");
        super.save(account);
    }


    @Override
    public void cardToCard(Account originAccount, Account destinationAccount, double amount) {
        withdraw(originAccount, amount, Values.CARD_TO_CARD);
        deposit(destinationAccount, amount);

        Transaction originTransaction = new Transaction(originAccount, TransactionType.WITHDRAW, TransactionStatus.WAITING, amount + Values.CARD_TO_CARD);
        Transaction destinationTransaction = new Transaction(destinationAccount, TransactionType.DEPOSIT, TransactionStatus.WAITING, amount);
        originAccount.addTransaction(originTransaction);
        destinationAccount.addTransaction(destinationTransaction);
        transactionRepo.create(originTransaction);
        transactionRepo.create(destinationTransaction);

        setCartToCart(originAccount, destinationAccount
                , originTransaction, destinationTransaction);
    }

    @Override
    public double takeInventory(Account account) {
        try {
            repository.getEntityManager().getTransaction().begin();
            withdraw(account, Values.INVENTORY);
            repository.update(account);
            repository.getEntityManager().getTransaction().commit();
            Transaction transaction = new Transaction(account, TransactionType.WITHDRAW
                    , TransactionStatus.SUCCESS, Values.INVENTORY);
            ApplicationContext.getTransactionService().save(transaction);
            return account.getBalance();
        } catch (Exception e) {
            repository.getEntityManager().getTransaction().rollback();
            throw e;
        }
    }
    @Override
    public double takeInventory(String accountNumber, String password) {
        Optional<Account> optionalAccount = load(accountNumber,password);
        if(optionalAccount.isEmpty())
            throw new EntityNotFoundException("There is no account with this account number and password!");
        return takeInventory(optionalAccount.get());
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

    private void setCartToCart(Account originAccount, Account destinationAccount, Transaction originTransaction, Transaction destinationTransaction) {
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

    private void checkAccountFormat(String accountNumber) {
        if (!accountNumber.matches(Values.ACCOUNT_NUMBER_REGEX))
            throw new IllegalArgumentException("Account number format is incorrect!");
    }

    private void deposit(Account account, double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Amount is less than zero!");
        account.deposit(amount);
    }

    @Override
    public Optional<Account> load(DebitCard debitCard) {
        return repository.read(debitCard);
    }

    @Override
    public Optional<Account> load(String accountNumber, String password) {
        checkAccountFormat(accountNumber);
        return repository.read(accountNumber, password);
    }
}
