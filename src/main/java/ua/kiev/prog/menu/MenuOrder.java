package ua.kiev.prog.menu;

import java.util.ArrayList;
import java.util.List;

public class MenuOrder {

    private final List<Menu> menuList;
    private final int weightLimit;

    public MenuOrder(final int weightLimit) {
        this.weightLimit = weightLimit;
        this.menuList = new ArrayList<>();
    }

    public boolean add(Menu menu) {
        boolean result = false;
        if (totalWeight() + menu.weight() <= weightLimit) {
            menuList.add(menu);
            result = true;
        }
        return result;
    }

    public int totalWeight() {
        int totalWeight = 0;
        for (Menu menu : menuList) {
            totalWeight += menu.weight();
        }
        return totalWeight;
    }

    @Override
    public String toString() {
        return "MenuOrder{" +
                "menuList=" + menuList +
                ", totalWeight=" + totalWeight() +
                '}';
    }
}
