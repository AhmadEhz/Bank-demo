package org.bank.repository;

import org.bank.base.repository.BaseRepository;
import org.bank.entity.DebitCard;

public interface DebitCardRepo extends BaseRepository<DebitCard,String > {
    boolean checkCvv2(DebitCard debitCard);
}
