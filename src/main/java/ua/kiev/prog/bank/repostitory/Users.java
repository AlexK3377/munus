package ua.kiev.prog.bank.repostitory;

import ua.kiev.prog.bank.entity.User;

import javax.persistence.EntityManager;
import java.util.Collection;

public class Users {
    private final EntityManager entityManager;

    public Users(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void saveAll(Collection<User> users) {
        entityManager.getTransaction().begin();
        try {
            for (User user : users) {
                entityManager.persist(user);
            }
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
        }
    }
}
