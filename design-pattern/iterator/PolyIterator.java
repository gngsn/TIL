package iterator;

public interface PolyIterator<T> {
    public boolean isNext();
    public T next();
}
