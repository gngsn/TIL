package iterator;

import java.util.Iterator;

public class Collection implements Aggregate {
    private Fruit[] obj = {};
    private Integer last = 0;

    public Fruit getObj(int index) {
        return this.obj[index];
    }

    public Integer getLast() {
        return this.last;
    }

    public void append(Fruit obj) {
        this.obj[last] = obj;
        this.last++;
    }

    @Override
    public void iterator() {
        return new IteratorObject(this);
    }
}
