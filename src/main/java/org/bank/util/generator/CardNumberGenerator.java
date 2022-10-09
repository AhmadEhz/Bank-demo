package org.bank.util.generator;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import java.io.Serializable;

public class CardNumberGenerator extends SequenceStyleGenerator {
    private static BaseGenerator baseGenerator = new BaseGenerator();
    private static boolean firstTime = true;
    private static final long BASE_ACCOUNT_NUMBER = 5068_7350_1010_1000L;
    private static final int RANDOM_BOUND = 24;
    private static final String SEQUENCE_NAME = "card_num_gen";

    @Override
    public Serializable generate(SharedSessionContractImplementor session,
                                 Object object) throws HibernateException {
        return baseGenerator.generate(session, SEQUENCE_NAME, BASE_ACCOUNT_NUMBER, RANDOM_BOUND);
    }
}
