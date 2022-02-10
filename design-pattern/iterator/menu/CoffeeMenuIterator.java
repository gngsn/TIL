package iterator.menu;

import java.util.ArrayList;

public class CoffeeMenuIterator implements Iterator<MenuItem>{
    ArrayList<MenuItem> list;
    int position = 0;

    public CoffeeMenuIterator (ArrayList<MenuItem> list) {
        this.list = list;
    }

    @Override
    public MenuItem next() {
        MenuItem menuItem = list.get(position);
        position += 1;
        return menuItem;
    }

    @Override
    public boolean hasNext() {
        if (position >= list.size() || list.get(position) == null) return false;
        return true;
    }
}
