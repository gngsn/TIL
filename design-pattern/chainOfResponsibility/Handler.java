package chainOfResponsibility;

public class Handler {
    public String event(String event) {
        Order first = new Order();
        first.setNext(new Cancel());

        return first.execute(event);
    }
}
