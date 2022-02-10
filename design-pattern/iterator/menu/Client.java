package iterator.menu;

public class Client {
    public static void main(String[] args) {
        CoffeeMenu coffeeMenu = new CoffeeMenu();
        NonCoffeeMenu nonCoffeeMenu = new NonCoffeeMenu();

        Iterator<MenuItem> coffee = coffeeMenu.createIterator();
        Iterator<MenuItem> nonCoffee= nonCoffeeMenu.createIterator();

        printMenu(coffee);
        printMenu(nonCoffee);
    }

    static void printMenu(Iterator<MenuItem> iterator) {
        while(iterator.hasNext()) {
            MenuItem item = iterator.next();
            System.out.print(item.getName() + " - ");
            System.out.println("$" + item.getPrice());
        }
    }
}
