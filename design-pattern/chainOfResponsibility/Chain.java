package chainOfResponsibility;

public abstract class Chain {
    protected Chain next;
    public void setNext(Chain obj) {
        this.next = obj;
    }
    abstract String execute(String event);
}
