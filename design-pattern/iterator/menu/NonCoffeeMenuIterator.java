package iterator.menu;
import java.util.Iterator;

public class NonCoffeeMenuIterator implements Iterator<MenuItem>{
    MenuItem[] list;
    int position = 0;

    public NonCoffeeMenuIterator(MenuItem[] list) {
        this.list = list;
    }

    @Override
    public MenuItem next() {
        MenuItem menuItem = list[position];
        position += 1;
        return menuItem;
    }

    @Override
    public boolean hasNext() {
        if (position >= list.length || list[position] == null) return false;
        return true;
    }
}
