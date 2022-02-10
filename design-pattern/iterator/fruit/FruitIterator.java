package iterator.fruit;

import java.util.Iterator;

public class FruitIterator implements Iterator {
    private Collection aggregate;
    private int index = 0;

    public FruitIterator(Collection aggregate) {
        this.aggregate = aggregate;
    }

    @Override
    public boolean hasNext() {
        if (this.index >= this.aggregate.getLast()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Fruit next() {
        Fruit obj = this.aggregate.getObj(this.index);
        this.index++;
        return obj;
    }
}
