package iterator.menu;

import java.util.ArrayList;
import java.util.Iterator;

public class CoffeeMenu implements Menu{
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

    @Override
    public Iterator<MenuItem> createIterator() {
        return new CoffeeMenuIterator(this.menuItems);
    }
}
