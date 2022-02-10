package iterator.menu;

import java.util.ArrayList;

public class CoffeeMenu {
    ArrayList<MenuItem> menuItems;

    public CoffeeMenu() {
        this.menuItems = new ArrayList();
        additem("Espresso", 4.5);
        additem("Long Black", 5);
        additem("Latte", 5.5);
        additem("Flat White", 5.5);
    }

    public void additem(String name, double price) {
        MenuItem menuItem = new MenuItem(name, price);
        this.menuItems.add(menuItem);
    }

    public ArrayList<MenuItem> getMenuItems() {
        return menuItems;
    }

}
