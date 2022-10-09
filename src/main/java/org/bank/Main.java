package org.bank;

import org.bank.entity.Account;
import org.bank.entity.Customer;
import org.bank.entity.DebitCard;
import org.bank.service.AccountService;
import org.bank.service.CustomerService;
import org.bank.service.DebitCardService;
import org.bank.util.ApplicationContext;

import java.math.BigDecimal;


public class Main {
    public static void main(String[] args) {
      /*  Customer customer = new Customer();
       // customer.setId(1L);
        customer.setAddress("sdfsdf");
        customer.setFirstName("sdfsaf");
        customer.setLastName("asdfdsaf");
        customer.setNationalCode("fsafds");
        customer.setPhoneNumber("sdfsadf");
        Account account = new Account(customer);
        Account account1 = new Account(customer);
        DebitCard debitCard1 = new DebitCard(account);
        DebitCard debitCard2 = new DebitCard(account1);
        account.deposit(20000);
        account1.deposit(30000);
        CustomerService customerService = ApplicationContext.getCustomerService();
        customerService.save(customer);

        AccountService accountService = ApplicationContext.getAccountService();
        DebitCardService debitCardService =ApplicationContext.getDebitCardService();

        debitCardService.save(debitCard1);
        debitCardService.save(debitCard2);
        accountService.cardToCard(debitCard1.getCardNumber(),debitCard2.getCardNumber(),5000);
    }*/
        BigDecimal bigDecimal = new BigDecimal(0);
       bigDecimal = bigDecimal.add(BigDecimal.valueOf(2000));
       bigDecimal =  bigDecimal.subtract(BigDecimal.valueOf(1000));
        System.out.println(bigDecimal.toString());
    }
}
