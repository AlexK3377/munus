package ua.kiev.prog.bank;

import com.github.javafaker.Faker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.kiev.prog.bank.entity.*;
import ua.kiev.prog.bank.repostitory.Accounts;
import ua.kiev.prog.bank.repostitory.Exchanges;
import ua.kiev.prog.bank.repostitory.Transactions;
import ua.kiev.prog.bank.repostitory.Users;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class BankTest {

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("bank");
    private EntityManager entityManager = entityManagerFactory.createEntityManager();
    private Accounts accounts = new Accounts(entityManager);
    private Users users = new Users(entityManager);
    private Exchanges exchanges = new Exchanges(entityManager);
    private Transactions transactions = new Transactions(entityManager);
    private BankOperations bankOperations = new BankOperations(accounts, transactions, exchanges, entityManager);
    private Faker faker = new Faker();

    @Before
    public void init() {
        initUsers(2, "USD");
        initExchanges();
    }

    @Test
    public void failedTransferMoney() {
        Account from = accounts.account(2);
        Account to = accounts.account(4);
        Transaction transaction = bankOperations.transfer(from, to, "USD", 10.0);
        Assert.assertEquals(TransactionState.FAILED, transaction.state());
    }

    @Test
    public void successTransferMoney() {
        Account from = accounts.account(2);
        bankOperations.replenish(from, "USD", 12.0);
        Account to = accounts.account(4);
        Transaction transaction = bankOperations.transfer(from, to, "USD", 10.0);
        Assert.assertEquals(TransactionState.SUCCESS, transaction.state());
    }

    private List<User> generate(int n, String ... currencies) {
        List<User> users = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            User user = new User(faker.funnyName().name());
            for (String currency : currencies) {
                user.add(new Account(currency, user));
            }
            users.add(user);
        }
        return users;
    }

    private void initUsers(int n, String ... currencies) {
        List<User> userList = generate(n, currencies);
        users.saveAll(userList);
    }

    private void initExchanges() {
        exchanges.add(new CurrencyExchange("UAH", "USD", 28.0));
        exchanges.add(new CurrencyExchange("USD", "UAH", 0.035714286));
    }
}
