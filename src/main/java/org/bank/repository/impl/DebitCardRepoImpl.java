package org.bank.repository.impl;

import org.bank.base.repository.BaseRepositoryImpl;
import org.bank.entity.DebitCard;
import org.bank.repository.DebitCardRepo;

import javax.persistence.EntityManager;
import javax.persistence.Query;

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
        return debitCard.getCvv2() == entityManager.createQuery("select cvv2 from DebitCard where cardNumber = :cardNum", Integer.class).setParameter("cardNum", debitCard.getCvv2()).getSingleResult();
    }
}
