package org.bank.service;

import org.bank.base.service.BaseService;
import org.bank.entity.Account;
import org.bank.entity.DebitCard;
import org.bank.repository.AccountRepo;

import java.util.Optional;

public interface AccountService extends BaseService<Account, String, AccountRepo> {
    Optional<Account> load(String nationalCode);


    void cardToCard(Account originAccount, Account destinationAccount, double amount);

    double takeInventory(Account account);

    Optional<Account> load(DebitCard debitCard);
}
