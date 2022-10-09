package org.bank.util;

import org.bank.repository.*;
import org.bank.repository.impl.*;
import org.bank.service.*;
import org.bank.service.impl.*;
import javax.persistence.EntityManagerFactory;
import java.util.Scanner;

public class ApplicationContext {
     private static final EntityManagerFactory entityManagerFactory = HibernateUtil.getEntityManagerFactory();
     public static final Scanner scanner = new Scanner(System.in);
     private static final AccountRepo accountRepo = new AccountRepoImpl(entityManagerFactory.createEntityManager());
     private static final DebitCardRepo debitCardRepo = new DebitCardRepoImpl(entityManagerFactory.createEntityManager());
     private static final CustomerRepo customerRepo = new CustomerRepoImpl(entityManagerFactory.createEntityManager());
     private static final TransactionRepo transactionRepo = new TransactionRepoImpl(entityManagerFactory.createEntityManager());
     private static final AccountService accountService = new AccountServiceImpl(accountRepo);
     private static final CustomerService customerService = new CustomerServiceImpl(customerRepo);
     private static final DebitCardService debitCardService = new DebitCardServiceImpl(debitCardRepo);

     public static AccountRepo getAccountRepo() {
          return accountRepo;
     }

     public static DebitCardRepo getDebitCardRepo() {
          return debitCardRepo;
     }

     public static CustomerRepo getCustomerRepo() {
          return customerRepo;
     }

     public static TransactionRepo getTransactionRepo() {
          return transactionRepo;
     }

     private static final TransactionService transactionService = new TransactionServiceImpl(transactionRepo);

     public static AccountService getAccountService() {
          return accountService;
     }

     public static CustomerService getCustomerService() {
          return customerService;
     }

     public static DebitCardService getDebitCardService() {
          return debitCardService;
     }

     public static TransactionService getTransactionService() {
          return transactionService;
     }
}
