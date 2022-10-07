package org.bank.repository.impl;

import org.bank.base.repository.BaseRepositoryImpl;
import org.bank.entity.DebitCard;
import org.bank.repository.DebitCardRepo;

import javax.persistence.EntityManager;

public class DebitCardRepoImpl extends BaseRepositoryImpl<DebitCard, String> implements DebitCardRepo {

    public DebitCardRepoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<DebitCard> getEntityClass() {
        return DebitCard.class;
    }

}
