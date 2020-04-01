package ua.kiev.prog.bank.repostitory;

import ua.kiev.prog.bank.entity.Account;
import ua.kiev.prog.bank.entity.Transaction;

import javax.persistence.EntityManager;

public class Transactions {

    private final EntityManager entityManager;

    public Transactions(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Transaction create(Account from, Account to, String currency, Double amount) {
        Transaction transaction =  new Transaction(from, to, currency, amount);
        entityManager.getTransaction().begin();
        try {
            transaction = entityManager.merge(transaction);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
        }
        return transaction;
    }

    public void success(Transaction transaction) {
        transaction.success();
        entityManager.persist(transaction);
    }

    public void failed(Transaction transaction) {
        transaction.failed();
        entityManager.persist(transaction);
    }
}
