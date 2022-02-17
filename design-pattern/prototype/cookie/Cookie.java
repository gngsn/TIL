package prototype.cookie;

public class Cookie implements Cloneable {
    public Object clone() {
        try {
            return (Cookie) super.clone();
        } catch(CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}