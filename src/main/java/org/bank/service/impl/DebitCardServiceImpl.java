package org.bank.service.impl;

import org.bank.base.service.BaseServiceImpl;
import org.bank.entity.Account;
import org.bank.entity.DebitCard;
import org.bank.repository.DebitCardRepo;
import org.bank.service.AccountService;
import org.bank.service.DebitCardService;
import org.bank.util.ApplicationContext;
import org.bank.util.exception.EntityNotFoundException;

import java.util.Optional;

public class DebitCardServiceImpl extends BaseServiceImpl<DebitCard,String, DebitCardRepo> implements DebitCardService {
    private AccountService accountService = ApplicationContext.getAccountService();
    public DebitCardServiceImpl(DebitCardRepo debitCardRepo) {
        super(debitCardRepo);
    }
    @Override
    public void cardToCard(String originDebitCardNumber, String destinationDebitCardNumber, double amount) {
        Optional<DebitCard> originDebitCard = repository.read(originDebitCardNumber);
        Optional<DebitCard> destinationDebitCard = repository.read(destinationDebitCardNumber);
        if (originDebitCard.isEmpty())
            throw new EntityNotFoundException("Origin Debit Card not found!");
        if (destinationDebitCard.isEmpty())
            throw new EntityNotFoundException("Origin Debit Card not found!");
        else
            accountService.cardToCard(originDebitCard.get().getAccount(), destinationDebitCard.get().getAccount(), amount);

    }
    public double takeInventory (DebitCard debitCard) {
        Account account = accountService.load(debitCard);
        return accountService.takeInventory(account);
    }
}
