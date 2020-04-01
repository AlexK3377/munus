package ua.kiev.prog.menu;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class Menus {

    private final EntityManager entityManager;

    public Menus(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void add(Menu menu) {
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(menu);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
        }
    }

    public long count() {
        Query query = entityManager.createQuery("SELECT COUNT(menu.id) FROM ua.kiev.prog.menu.Menu menu");
        return (Long) query.getSingleResult();
    }

    public void deleteAll() {
        entityManager.getTransaction().begin();
        try {
            Query query = entityManager.createQuery("DELETE FROM ua.kiev.prog.menu.Menu");
            query.executeUpdate();
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
        }
    }

    @SuppressWarnings("unchecked")
    public List<Menu> menus() {
        Query query = entityManager.createQuery("SELECT menu FROM ua.kiev.prog.menu.Menu menu");
        return (List<Menu>) query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Menu> menus(Integer priceFrom,
                            Integer priceTo) {
        Query query = entityManager.createQuery(
                "SELECT menu FROM ua.kiev.prog.menu.Menu menu WHERE menu.price >= :priceFrom AND menu.price <= :priceTo"
        );
        query.setParameter("priceFrom", priceFrom);
        query.setParameter("priceTo", priceTo);
        return (List<Menu>) query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Menu> menusWithDiscount() {
        Query query = entityManager.createQuery(
                "SELECT menu FROM ua.kiev.prog.menu.Menu menu WHERE menu.discount > 0"
        );
        return (List<Menu>) query.getResultList();
    }

    public MenuOrder menuOrder(Integer totalWeight) {
        MenuOrder menuOrder = new MenuOrder(totalWeight);
        List<Menu> menuList = menus();
        for (Menu menu : menuList) {
            menuOrder.add(menu);
        }
        return menuOrder;
    }

}
