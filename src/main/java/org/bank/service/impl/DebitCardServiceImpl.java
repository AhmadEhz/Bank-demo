package org.bank.service.impl;

import org.bank.base.service.BaseServiceImpl;
import org.bank.entity.Account;
import org.bank.entity.DebitCard;
import org.bank.repository.DebitCardRepo;
import org.bank.service.AccountService;
import org.bank.service.DebitCardService;
import org.bank.util.ApplicationContext;
import org.bank.util.Values;
import org.bank.util.exception.EntityNotFoundException;
import org.bank.util.exception.SuspendedCardException;

import java.time.LocalDate;
import java.util.Optional;

public class DebitCardServiceImpl extends BaseServiceImpl<DebitCard, String, DebitCardRepo> implements DebitCardService {
    private AccountService accountService = ApplicationContext.getAccountService();

    public DebitCardServiceImpl(DebitCardRepo debitCardRepo) {
        super(debitCardRepo);
    }

    @Override
    public void cardToCard(String originDebitCardNumber, int cvv2, LocalDate expirationDate, String destinationDebitCardNumber, double amount) {
        checkCardNumberFormat(originDebitCardNumber);
        checkCardNumberFormat(destinationDebitCardNumber);
        checkCvv2Format(cvv2);

        Optional<DebitCard> originDebitCard = repository.read(originDebitCardNumber);
        Optional<DebitCard> destinationDebitCard = repository.read(destinationDebitCardNumber);
        if (originDebitCard.isEmpty())
            throw new EntityNotFoundException("Origin Debit Card not found!");

        if (destinationDebitCard.isEmpty())
            throw new EntityNotFoundException("Destination Debit Card not found!");

        if (originDebitCard.get().getCvv2() != cvv2 || !originDebitCard.get().getExpirationDate().equals(expirationDate)) {
            increaseIncorrectPassword(originDebitCardNumber);
            throw new IllegalArgumentException("your cvv2 or expiration date is incorrect!");
        } else
            accountService.cardToCard(originDebitCard.get().getAccount(), destinationDebitCard.get().getAccount(), amount);
    }

    private void increaseIncorrectPassword(String cardNumber) {
        try {
            repository.getEntityManager().getTransaction().begin();
            repository.increaseIncorrectPassword(cardNumber);
            repository.getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            repository.getEntityManager().getTransaction().rollback();
            throw e;
        }
    }

    private void checkCardNumberFormat(String cardNumber) {
        if (!cardNumber.matches(Values.CARD_NUMBER_REGEX))
            throw new IllegalArgumentException("Card number format is incorrect!");
    }

    private void checkCvv2Format(int cvv2) {
        if (!String.valueOf(cvv2).matches(Values.CVV2_REGEX))
            throw new IllegalArgumentException("Cvv2 format is incorrect!");
    }

    @Override
    public double takeInventory(String cardNumber, int cvv2) {
        checkCardNumberFormat(cardNumber);
        checkCvv2Format(cvv2);
        Optional<Account> optionalAccount = accountService.load(new DebitCard(cardNumber, cvv2));
        if (optionalAccount.isEmpty()) {
            if (!isExist(cardNumber))
                throw new EntityNotFoundException("this card number is not found!");
            else {
                increaseIncorrectPassword(cardNumber);
                throw new IllegalArgumentException("Your cvv2 was entered is incorrect!");
            }
        }
        return accountService.takeInventory(optionalAccount.get());
    }

    @Override
    public void update(DebitCard debitCard) {
        Optional<DebitCard> optionalDebitCard = repository.read(debitCard.getCardNumber());
        checkSuspend(optionalDebitCard);
        super.update(debitCard);
    }

    @Override
    public Optional<DebitCard> load(String cardNumber) {
        Optional<DebitCard> optionalDebitCard = repository.read(cardNumber);
        checkSuspend(optionalDebitCard);
        return optionalDebitCard;
    }


    private void checkSuspend(Optional<DebitCard> optionalDebitCard) {
        if (optionalDebitCard.isPresent())
            if (optionalDebitCard.get().isSuspended())
                throw new SuspendedCardException("this card is suspended!");
    }

}
