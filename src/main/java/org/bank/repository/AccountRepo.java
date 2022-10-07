package org.bank.repository;

import org.bank.base.repository.BaseRepository;
import org.bank.entity.Account;

import java.util.Optional;

public interface AccountRepo extends BaseRepository<Account, String> {
}
