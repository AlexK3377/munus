package ua.kiev.prog.bank;

import ua.kiev.prog.bank.entity.Account;
import ua.kiev.prog.bank.entity.Transaction;
import ua.kiev.prog.bank.repostitory.Accounts;
import ua.kiev.prog.bank.repostitory.Exchanges;
import ua.kiev.prog.bank.repostitory.Transactions;

import javax.persistence.EntityManager;

public class BankOperations {

    private final Accounts accounts;
    private final Transactions transactions;
    private final Exchanges exchanges;
    private final EntityManager entityManager;

    public BankOperations(Accounts accounts, Transactions transactions, Exchanges exchanges, EntityManager entityManager) {
        this.accounts = accounts;
        this.transactions = transactions;
        this.exchanges = exchanges;
        this.entityManager = entityManager;

    }

    public Transaction replenish(Account to, String currency, Double amount) {
        Transaction transaction = transactions.create(null, to, currency, amount);
        Double replenishRate = exchanges.rate(to.currency(), currency);
        try {
            entityManager.getTransaction().begin();
            to.replenish(replenishRate * amount);
            accounts.save(to);
            transactions.success(transaction);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            transactions.failed(transaction);
        }
        return transaction;
    }

    public Transaction transfer(Account from, Account to, String currency, Double amount) {
        Transaction transaction = transactions.create(from, to, currency, amount);
        Double withdrawalRate = exchanges.rate(currency, from.currency());
        Double replenishRate = exchanges.rate(to.currency(), currency);
        try {
            entityManager.getTransaction().begin();
            from.withdraw(withdrawalRate * amount);
            to.replenish(replenishRate * amount);
            accounts.save(from);
            accounts.save(to);
            transactions.success(transaction);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            transactions.failed(transaction);
        }
        return transaction;
    }
}
