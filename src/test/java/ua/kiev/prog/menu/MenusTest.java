package ua.kiev.prog.menu;

import com.github.javafaker.Faker;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class MenusTest {

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("menus");
    private Menus menus = new Menus(entityManagerFactory.createEntityManager());
    private Faker faker = new Faker();

    @After
    public void cleanup() {
        menus.deleteAll();
    }

    @Test
    public void allfine() {
        Assert.assertTrue(true);
    }

    @Test
    public void addMenu() {
        int n = 10;
        List<Menu> menuList = generateMenuList(n);
        for (Menu menu : menuList) {
            menus.add(menu);
        }
        Assert.assertEquals(n, menus.count());
    }

    @Test
    public void menusByPrice() {
        int n = 10;
        int priceFrom = 20;
        int priceTo = 45;
        List<Menu> menuList = generateMenuList(n);
        for (Menu menu : menuList) {
            menus.add(menu);
        }
        List<Menu> result = this.menus.menus(priceFrom, priceTo);
        for (Menu menu : result) {
            int price = menu.price();
            Assert.assertTrue(price >= priceFrom && price <= priceTo);
        }
    }

    @Test
    public void menuOrder() {
        int n = 10;
        int totalWeight = 1000;
        List<Menu> menuList = generateMenuList(n);
        for (Menu menu : menuList) {
            menus.add(menu);
        }
        MenuOrder menuOrder = menus.menuOrder(totalWeight);
        Assert.assertTrue(menuOrder.totalWeight() <= totalWeight);
        System.out.println(menuOrder);
    }

    private List<Menu> generateMenuList(int n) {
        List<Menu> menuList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            menuList.add(
                    new Menu(
                            faker.commerce().productName(),
                            faker.number().numberBetween(1, 100),
                            faker.number().numberBetween(100, 1000),
                            faker.number().numberBetween(0, 10)
                    )
            );
        }
        return menuList;
    }
}
