package prototype.cookie;

public class Cookie implements Cloneable {
    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch(CloneNotSupportedException e) {
            return null;
        }
    }
}