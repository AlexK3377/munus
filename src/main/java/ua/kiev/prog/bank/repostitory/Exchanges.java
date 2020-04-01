package ua.kiev.prog.bank.repostitory;

import ua.kiev.prog.bank.entity.CurrencyExchange;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class Exchanges {

    private final EntityManager entityManager;

    public Exchanges(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void add(CurrencyExchange currencyExchange) {
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(currencyExchange);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
        }
    }

    public Double rate(String currency1, String currency2) {
        if (currency1.equals(currency2)) {
            return 1.0;
        }
        Query query = entityManager.createQuery(
                "SELECT exchange FROM ua.kiev.prog.bank.entity.CurrencyExchange exchange " +
                        "WHERE exchange.currency1 = :currency1 AND exchange.currency2 = :currency2"
        );
        query.setParameter("currency1", currency1);
        query.setParameter("currency2", currency2);
        CurrencyExchange currencyExchange = (CurrencyExchange) query.getSingleResult();
        if (currencyExchange == null) {
            throw new RuntimeException("Currency exchange not found");
        }
        return currencyExchange.rate();
    }

}
