package org.bank.repository.impl;

import org.bank.base.repository.BaseRepositoryImpl;
import org.bank.entity.CreditCard;
import org.bank.repository.CreditCardRepo;

import javax.persistence.EntityManager;

public class CreditCardRepoImpl extends BaseRepositoryImpl<CreditCard, String> implements CreditCardRepo {

    public CreditCardRepoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<CreditCard> getEntityClass() {
        return CreditCard.class;
    }

}
