package adapter.EnumIterObject;

import java.util.Enumeration;
import java.util.Iterator;

public class EnumerationIterator implements Iterator {
    Enumeration<String> adaptee;

    @Override
    public boolean hasNext() {
        return this.adaptee.hasMoreElements();
    }

    @Override
    public Object next() {
        return this.adaptee.nextElement();
    }
}
