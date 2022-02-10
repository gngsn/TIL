package iterator.menu;

import java.util.ArrayList;

public class Client {
    public static void main(String[] args) {
        CoffeeMenu coffeeMenu = new CoffeeMenu();
        NonCoffeeMenu nonCoffeeMenu = new NonCoffeeMenu();

        ArrayList<MenuItem> coffee = coffeeMenu.getMenuItems();
        MenuItem[] nonCoffee = nonCoffeeMenu.getMenuItems();

        System.out.println("Menu");

        for (int i = 0; i < coffee.size(); i++) {
            System.out.print(coffee.get(i).getName() + " - ");
            System.out.println("$" + coffee.get(i).getPrice());
        }

        for (int i = 0; i < nonCoffee.length; i++) {
            System.out.print(nonCoffee[i].getName() + " - ");
            System.out.println("$" + nonCoffee[i].getPrice());
        }
    }
}
