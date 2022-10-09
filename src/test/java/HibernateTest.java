import org.bank.entity.Account;
import org.bank.entity.Customer;
import org.bank.entity.DebitCard;
import org.bank.service.AccountService;
import org.bank.service.CustomerService;
import org.bank.service.DebitCardService;
import org.bank.service.TransactionService;
import org.bank.util.ApplicationContext;
import org.bank.util.Wage;
import org.bank.util.exception.EntityNotFoundException;
import org.bank.util.exception.InsufficientMoneyException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.NoSuchElementException;

public class HibernateTest {
    //For test, the "hibernate.hbm2ddl.auto" should be on "create-drop"
    private static final AccountService accountService = ApplicationContext.getAccountService();
    private static final DebitCardService debitCardService = ApplicationContext.getDebitCardService();
    private static final CustomerService customerService = ApplicationContext.getCustomerService();
    private static final TransactionService transactionService = ApplicationContext.getTransactionService();

    static EntityManager entityManager;
    private static Customer customer1 = new Customer("Ali", "Abbasi", "Tehran", "0371234567", "0912000000");
    private static Customer customer2 = new Customer("Hosein", "Mousavi", "Isfahan", "0329876543", "0993123456");
    private static Account account1 = new Account(customer1, 500_000D);
    private static Account account2 = new Account(customer1, 150_000D);
    private static DebitCard debitCard1 = new DebitCard(account1);
    private static DebitCard debitCard2 = new DebitCard(account2);
    private static Account account3 = new Account(customer2, 230_000D);
    private static DebitCard debitCard3 = new DebitCard(account3);

    @BeforeAll
    static void createEntities() {
        debitCardService.save(debitCard1);
        debitCardService.save(debitCard2);
        debitCardService.save(debitCard3);
    }

    @Test
    void testSavedEntities() {
        assertEquals(customer1, customerService.load(customer1.getId()).get());
        assertEquals(customer2, customerService.load(customer2.getId()).get());
        assertEquals(account1, accountService.load(account1.getAccountNumber()).get());
        assertEquals(account2, accountService.load(account2.getAccountNumber()).get());
        assertEquals(account3, accountService.load(account3.getAccountNumber()).get());
        assertEquals(debitCard1, debitCardService.load(debitCard1.getCardNumber()).get());
        assertEquals(debitCard2, debitCardService.load(debitCard2.getCardNumber()).get());
        assertEquals(debitCard3, debitCardService.load(debitCard3.getCardNumber()).get());
    }

    @Test
    void cardToCard() {
        double oldOriginalBalance = account1.getBalance();
        double oldDestinationBalance = account3.getBalance();
        double amount = 30_000;
        accountService.cardToCard(account1, account3, amount);
        assertEquals(oldOriginalBalance - amount - Wage.CARD_TO_CARD, accountService.load
                (account1.getAccountNumber()).get().getBalance());
        assertEquals(oldDestinationBalance + amount, accountService.load
                (account3.getAccountNumber()).get().getBalance());
    }

    @Test
    void cardToCardByCardNumber() {
        double oldOriginalBalance = debitCard2.getBalance();
        double oldDestinationBalance = debitCard3.getBalance();
        String originalCardNumber = debitCard2.getCardNumber();
        String destinationCardNumber = debitCard3.getCardNumber();
        double amount = 40_000;
        debitCardService.cardToCard(originalCardNumber, destinationCardNumber, amount);
        assertEquals(oldOriginalBalance - amount - Wage.CARD_TO_CARD, debitCardService.load(originalCardNumber)
                .get().getBalance());
        assertEquals(oldDestinationBalance + amount, debitCardService.load(destinationCardNumber).get().getBalance());
    }

    @Test
    void checkCardNumber() {
        assertThrows(NoSuchElementException.class, () ->debitCardService.load(debitCard1.getCardNumber()+"1").get());
        assertThrows(EntityNotFoundException.class, () -> debitCardService.cardToCard(debitCard1.getCardNumber(),debitCard2.getCardNumber()+"0",5000));
        assertThrows(EntityNotFoundException.class, () -> debitCardService.cardToCard(debitCard1.getCardNumber().replace("1","2"),debitCard2.getCardNumber(),5000));
    }
    @Test
    void checkAmount() {
        assertThrows(InsufficientMoneyException.class,() -> debitCardService.cardToCard(debitCard1.getCardNumber(),debitCard3.getCardNumber(),700_000));
        debitCard2.setBalance(100);
        debitCardService.update(debitCard2);
        assertThrows(InsufficientMoneyException.class, () -> debitCardService.get)
    }
}
