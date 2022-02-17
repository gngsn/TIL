package memento.basic;

public class Memento {
    protected String obj;

    public Memento(String obj) {
        this.obj = obj;
    }

    public String getObj() {
        return obj;
    }
}
