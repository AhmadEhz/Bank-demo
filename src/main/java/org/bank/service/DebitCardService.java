package org.bank.service;

import org.bank.base.service.BaseService;
import org.bank.entity.DebitCard;
import org.bank.repository.DebitCardRepo;

public interface DebitCardService extends BaseService<DebitCard,String , DebitCardRepo> {
    void cardToCard(String originDebitCardNumber, String destinationDebitCardNumber, double amount);
}
