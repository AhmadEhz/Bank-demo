package org.bank.repository.impl;

import org.bank.base.repository.BaseRepositoryImpl;
import org.bank.entity.DebitCard;
import org.bank.repository.DebitCardRepo;
import org.bank.util.Values;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;

public class DebitCardRepoImpl extends BaseRepositoryImpl<DebitCard, String> implements DebitCardRepo {

    public DebitCardRepoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<DebitCard> getEntityClass() {
        return DebitCard.class;
    }

    @Override
    public boolean checkCvv2(DebitCard debitCard) {
        try {
            return debitCard.getCvv2() == entityManager.createQuery
                            ("select cvv2 from DebitCard where cardNumber = :cardNum", Integer.class)
                    .setParameter("cardNum", debitCard.getCvv2()).getSingleResult();
        }
        catch (NoResultException e ) {
            return false;
        }
    }

    @Override
    public void increaseIncorrectPassword(String cardNumber) {
        Optional<DebitCard> optionalDebitCard = read(cardNumber);
        if (optionalDebitCard.isPresent()) {
            optionalDebitCard.get().increaseNumberOfIncorrectPasswordEntered();
            if (optionalDebitCard.get().getNumberOfIncorrectPasswordEntered() >= Values.NUMBER_OF_INCORRECT_PASSWORD)
                optionalDebitCard.get().setSuspended(true);
            update(optionalDebitCard.get());
        }
    }
}
