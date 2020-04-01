package ua.kiev.prog.bank.repostitory;

import ua.kiev.prog.bank.entity.Account;

import javax.persistence.EntityManager;

public class Accounts {

    private final EntityManager entityManager;

    public Accounts(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Account account) {
        entityManager.merge(account);
    }

    public Account account(Integer id) {
        return entityManager.find(Account.class, id);
    }

}
