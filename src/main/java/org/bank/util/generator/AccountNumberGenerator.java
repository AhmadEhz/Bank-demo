package org.bank.util.generator;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Random;

public class AccountNumberGenerator extends SequenceStyleGenerator {
    private static BaseGenerator baseGenerator = new BaseGenerator();
    private static boolean firstTime = true;
    private static final long BASE_ACCOUNT_NUMBER = 1234567890123L;
    private static final int RANDOM_BOUND = 12;
    private static final String SEQUENCE_NAME = "acc_num_seq";

    @Override
    public Serializable generate(SharedSessionContractImplementor session,
                                 Object object) throws HibernateException {
        return baseGenerator.generate(session, SEQUENCE_NAME, BASE_ACCOUNT_NUMBER, RANDOM_BOUND);
    }
    /*@Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        TypedQuery<BigInteger> query = session.createSQLQuery("select nextval(:seq)")
                .setParameter("seq",SEQUENCE_NAME);
        return query.getSingleResult().longValue() + "";
    }*/
}
