package org.bank.repository;

import org.bank.base.repository.BaseRepository;
import org.bank.entity.Account;
import org.bank.entity.DebitCard;

import java.util.Optional;

public interface AccountRepo extends BaseRepository<Account, String> {
    Optional<Account> load(DebitCard debitCard);
}
