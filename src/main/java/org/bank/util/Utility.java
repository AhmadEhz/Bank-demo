package org.bank.util;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Random;

public class Utility {
    private static final Random random = new Random();

    public static int randomGenerator(int bound) {
        return random.nextInt(bound);
    }
}
