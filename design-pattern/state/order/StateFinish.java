package state.order;

public class StateFinish implements State {
    @Override
    public void process() {
        System.out.println("처리완료");
    }
}
