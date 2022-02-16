package state.order;

public class StateOrdered implements State {
    @Override
    public void process() {
        System.out.println("주문완료");
    }
}
