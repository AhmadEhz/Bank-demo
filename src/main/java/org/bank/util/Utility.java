package org.bank.util;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Random;

public class Utility {
    private static final EntityManager entityManager = HibernateUtil.createEntityManager();
    private static final Random random = new Random();

    public static long generateNumber(String query) {
        try {
            return entityManager.createQuery(query, long.class).setMaxResults(1).getSingleResult();
        } catch (NoResultException e) {
            return 0;
        }
    }

    public static int randomGenerator(int bound) {
        return random.nextInt(bound);
    }
}
