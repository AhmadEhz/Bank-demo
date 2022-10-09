package org.bank.util.generator;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class BaseGenerator {
    private static final Random random = new Random();
    private boolean firstTime = true;

    public Serializable generate(SharedSessionContractImplementor session,
                                 String sequenceName, long baseNumber, int randomBound) throws HibernateException {
        Connection connection = session.connection();
        if (firstTime) {
            try (PreparedStatement ps = connection.prepareStatement("create sequence if not exists " + sequenceName)) {
                ps.execute();
            } catch (SQLException e) {
                throw new HibernateException(e);
            }
            firstTime = false;
        }
        try {
            PreparedStatement ps = connection.prepareStatement("select nextval(\'" + sequenceName + "\')");
            ResultSet rs = ps.executeQuery();
            rs.next();
            long minRandom = rs.getLong(1) * randomBound;
            long maxRandom = minRandom + randomBound;
            long number = random.nextLong(minRandom, maxRandom);
            return number + baseNumber + "";

        } catch (Exception e) {
            System.err.println("Can't generate account number!");
            e.printStackTrace();
        }
        return null;
    }
}
