package adapter.EnumerationIterator;

import java.util.Enumeration;
import java.util.Iterator;

public class EnumerationIterator implements Enumeration, Iterator {


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
