package org.bank.service;

import org.bank.base.service.BaseService;
import org.bank.entity.DebitCard;
import org.bank.repository.DebitCardRepo;

import java.time.LocalDate;

public interface DebitCardService extends BaseService<DebitCard,String , DebitCardRepo> {

    void cardToCard(String originDebitCardNumber, int cvv2, LocalDate expirationDate, String destinationDebitCardNumber, double amount);

    double takeInventory(String cardNumber, int cvv2);
}
