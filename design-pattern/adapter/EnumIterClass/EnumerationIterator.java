package adapter.EnumIterClass;

import java.util.Enumeration;
import java.util.Iterator;

public class EnumerationIterator implements Iterator, Enumeration {

//    Enumeration

    @Override
    public boolean hasMoreElements() {
        /* TODO */
        return false;
    }

    @Override
    public Object nextElement() {
        /* TODO */
        return null;
    }

//    Iterator


    @Override
    public boolean hasNext() {
        return this.hasMoreElements();
    }

    @Override
    public Object next() {
        return this.nextElement();
    }

}
