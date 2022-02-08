package iterator;

public class IteratorObject implements PolyIterator {
    private Collection aggregate;
    private int index = 0;

    public IteratorObject(Collection aggregate) {
        this.aggregate = aggregate;
    }

    @Override
    public boolean isNext() {
        if (this.index >= this.aggregate.getLast()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Object next() {
        Fruit obj = this.aggregate.getObj(this.index);
        this.index++;
        return obj;
    }
}
