package state.order;

public class StatePay implements State {
    @Override
    public void process() {
        System.out.println("결제중");
    }
}
