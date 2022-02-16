package state.order;

public class StateOrder implements State {
    @Override
    public void process() {
        System.out.println("주문");
    }
}
