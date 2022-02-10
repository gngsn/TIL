package iterator.menu;

public class NonCoffeeMenu implements Menu {
    static final int MAX_ITEMS = 4;
    int numberOfItems = 0;
    MenuItem[] menuItems;

    public NonCoffeeMenu() {
        this.menuItems = new MenuItem[MAX_ITEMS];
        additem("Lime Passion Tea", 4.5);
        additem("Grapefruit Honey Tea", 5);
        additem("Vin Chaud", 5.5);
        additem("Milk Shake", 5.5);
    }

    public void additem(String name, double price) {
        MenuItem menuItem = new MenuItem(name, price);
        if(this.numberOfItems >= MAX_ITEMS){
            System.err.println("죄송합니다, 메뉴가 꽉 찼습니다. 더 이상 추가할 수 없습니다.");
        } else {
            menuItems[numberOfItems] = menuItem;
            numberOfItems = numberOfItems+1;
        }
    }

    @Override
    public Iterator<MenuItem> createIterator() {
        return new NonCoffeeMenuIterator(this.menuItems);
    }
}
