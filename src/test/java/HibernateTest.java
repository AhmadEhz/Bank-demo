import org.bank.entity.*;
import org.bank.service.AccountService;
import org.bank.service.CustomerService;
import org.bank.service.DebitCardService;
import org.bank.service.TransactionService;
import org.bank.util.ApplicationContext;
import org.bank.util.Values;
import org.bank.util.exception.EntityNotFoundException;
import org.bank.util.exception.InsufficientMoneyException;
import org.bank.util.exception.SuspendedCardException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.PersistenceException;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;
import java.util.Set;

public class HibernateTest {
    //IMPORTANT NOTE: For test, the "hibernate.hbm2ddl.auto" should be on "create-drop"
    private static final AccountService accountService = ApplicationContext.getAccountService();
    private static final DebitCardService debitCardService = ApplicationContext.getDebitCardService();
    private static final CustomerService customerService = ApplicationContext.getCustomerService();
    private static final TransactionService transactionService = ApplicationContext.getTransactionService();

    private static Customer customer1 = new Customer("Ali", "Abbasi", "Tehran"
            , "0371234567", "0912000000");
    private static Customer customer2 = new Customer("Hosein", "Mousavi", "Isfahan"
            , "0329876543", "0993123456");
    private static Account account1 = new Account(customer1, 500_000D, "Aa123456");
    private static Account account2 = new Account(customer1, 150_000D, "43210Abc");
    private static DebitCard debitCard1 = new DebitCard(account1);
    private static DebitCard debitCard2 = new DebitCard(account2);
    private static Account account3 = new Account(customer2, 230_000D, "abcd1234");
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
        assertEquals(oldOriginalBalance - amount - Values.CARD_TO_CARD, accountService.load
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
        debitCardService.cardToCard(originalCardNumber, debitCard2.getCvv2()
                , debitCard2.getExpirationDate(), destinationCardNumber, amount);
        assertEquals(oldOriginalBalance - amount - Values.CARD_TO_CARD, debitCardService.load(originalCardNumber)
                .get().getBalance());
        assertEquals(oldDestinationBalance + amount, debitCardService.load(destinationCardNumber).get().getBalance());
    }

    @Test
    void takeInventory() {
        double oldBalance = debitCard2.getBalance();
        assertEquals(oldBalance - Values.INVENTORY, debitCardService.takeInventory(debitCard2.getCardNumber(), debitCard2.getCvv2()));
        assertEquals(oldBalance - Values.INVENTORY, accountService.load(debitCard2).get().getBalance());
        assertFalse(accountService.load(debitCard2).get().getTransactions().isEmpty());
    }

    @Test
    void checkCardNumber() {
        assertThrows(NoSuchElementException.class, () ->
                debitCardService.load(debitCard1.getCardNumber() + "1").get());
        assertThrows(IllegalArgumentException.class, () -> debitCardService.cardToCard(debitCard1.getCardNumber()
                , debitCard1.getCvv2(), debitCard1.getExpirationDate()
                , debitCard2.getCardNumber() + "0", 5000));
        assertThrows(EntityNotFoundException.class, () ->
                debitCardService.cardToCard(debitCard1.getCardNumber().replace("1", "2")
                        , debitCard1.getCvv2(), debitCard1.getExpirationDate()
                        , debitCard2.getCardNumber(), 5000));
    }

    @Test
    void checkAmount() {
        assertThrows(InsufficientMoneyException.class, () -> debitCardService.cardToCard(debitCard1.getCardNumber()
                , debitCard1.getCvv2(), debitCard1.getExpirationDate(), debitCard3.getCardNumber(), 700_000));
        account2.setBalance(100);
        accountService.update(account2);
        assertThrows(InsufficientMoneyException.class, () -> debitCardService.takeInventory(debitCard2.getCardNumber(), debitCard2.getCvv2()));
    }

    @Test
    void checkSavedTransaction() {
        cardToCard();
        Set<Transaction> originTransactions = accountService.load(account1.getAccountNumber()).get().getTransactions();
        Set<Transaction> destinationTransactions = accountService.load(account3.getAccountNumber()).get().getTransactions();
        assertFalse(originTransactions.isEmpty());
        assertFalse(destinationTransactions.isEmpty());
    }

    @Test
    void loadAccountByPassword() {
        assertEquals(account1, accountService.load(account1.getAccountNumber(), account1.getPassword()).get());
    }

    @Test
    void checkWeakPassword() {
        Account account1 = new Account(customer2, 100_000, "12345678");
        Account account2 = new Account(customer2, 100_000, "Aa444");
        Account account3 = new Account(customer2, 100_000, "AbCdEFGhIj");
        assertThrows(IllegalArgumentException.class, () -> accountService.save(account1));
        assertThrows(IllegalArgumentException.class, () -> accountService.save(account2));
        assertThrows(IllegalArgumentException.class, () -> accountService.save(account3));
    }

    @Test
    void SaveDuplicateEntityTest() {
        assertThrows(PersistenceException.class, () -> customerService.save(new Customer("Hamed"
                , "Tehrani", "Ahvaz"
                , "0329876543", "09903338899")));
        //National code is repeated.
    }

    @Test
    void showAllCustomerAccounts() {
        Customer customer = customerService.load(customer1.getNationalCode()).get();
        assertEquals(customerService.getAccounts(customer1), customerService.getAccounts(customer));
        System.out.println(customer.getAccounts());
        assertTrue(customerService.getAccounts(customer1).size() > customerService.getAccounts(customer2).size());
    }

    @Test
    void suspendAccountAfter3IncorrectPasswordEntered() {
        assertThrows(IllegalArgumentException.class,() ->debitCardService.cardToCard(debitCard3.getCardNumber(),1111
                ,debitCard3.getExpirationDate(),debitCard1.getCardNumber(),50_000));
        assertThrows(IllegalArgumentException.class,() -> debitCardService.takeInventory(debitCard3.getCardNumber(),1111));
        assertFalse(debitCardService.load(debitCard3.getCardNumber()).get().isSuspended());
        assertThrows(IllegalArgumentException.class, () -> debitCardService.cardToCard(debitCard3.getCardNumber(),1111
                ,debitCard3.getExpirationDate(),debitCard1.getCardNumber(),50_000));
        assertThrows(SuspendedCardException.class, () -> debitCardService.load(debitCard3.getCardNumber()));
    }
}
